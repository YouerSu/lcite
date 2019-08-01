package parser

import lib.Operation
import java.util.*

enum class Setting(var bool: Boolean){
    CALL_BY_VALUE(false),
}

class Data(
    val type: Identity,
    val line: Int,
    val col: Int
){
    fun DataError(message: String): Nothing =
        error("Error:$message in ($line,$col)")
}

abstract class Node(val data: Data){
    abstract fun eval(): Any
}

class ASTNode(val op: RootNode,val parameters: LinkedList<ValueNode>): Node(Data(Identity.Invoke,op.data.line,op.data.col)){
    override fun eval(): Any {
        op.bind(parameters)
        return op.eval()
    }
}

abstract class RootNode(line: Int,col: Int): Node(Data(Identity.Procedure,line, col)){
    abstract var parameters: LinkedList<ValueNode>
    abstract fun bind(values: LinkedList<ValueNode>)
}
class AtomicRootNode(val body: Operation, data: Data): RootNode(data.line,data.col){
    override lateinit var parameters: LinkedList<ValueNode>

    override fun eval(): Any {
        //Env.intoEnv()
        return body.procedure(parameters)
        //Env.leftEnv()
        //return result

    }

    override fun bind(values: LinkedList<ValueNode>) {
        parameters = values
    }
}
class CombintorRootNode(val body: ValueNode,override var parameters: LinkedList<ValueNode>):RootNode(body.data.line, body.data.col){

    override fun eval(): Any {
        val value = body.eval()
        parameters.forEach { Env.untied(it.value as String) }
        Env.leftEnv()
        return value
    }

    override fun bind(values: LinkedList<ValueNode>) {
        if (values.size != parameters.size){
            error("Wrong parameter table")
        }else if (Setting.CALL_BY_VALUE.bool){
            val args = values.map { ValueNode(it.eval(),Data(Identity.UnKnow,it.data.line,it.data.col)) }
            Env.intoEnv()
            for (index in 0..args.lastIndex){
                Env.bind(parameters[index].value.toString(),args[index])
            }
        }else {
            val args = values.map { EnvNode.pack(it) }
            Env.intoEnv()
            for (index in 0..args.lastIndex){
                Env.bind(parameters[index].value.toString(),args[index])
            }
        }
    }
}
class UnSolveRootNode(val body: ValueNode): RootNode(body.data.line,body.data.col){
    private lateinit var rootBody: RootNode
    override lateinit var parameters: LinkedList<ValueNode>
    override fun eval(): Any {
        return rootBody.eval()
    }

    override fun bind(values: LinkedList<ValueNode>) {
        rootBody = body.eval() as RootNode
        parameters = rootBody.parameters
        rootBody.bind(values)
    }
}

open
class ValueNode(val value: Any,data: Data): Node(data){
    override fun eval(): Any =
        when{//Sometimes RootNode will be arg
            value is ValueNode -> value.eval()
            value is ASTNode -> value.eval()
            data.type == Identity.Var -> Env.lookUp(value as String).eval()
            else -> value
        }
}

class EnvNode(value: Node, val deep: Int = Env.nowDeep): ValueNode(value,value.data){
    override fun eval(): Any {
        val deepOfEnv = Env.deepOfEnv
        val toDeep = Env.toDeep
        val nowDeep = Env.nowDeep
        Env.toEnv(deep)
        val result = (value as Node).eval()
        Env.outEnv(toDeep,nowDeep,deepOfEnv)
        return result
    }

    companion object{
        fun pack(node: ValueNode): ValueNode{
            return when{
                node is EnvNode -> node
                node.value is ASTNode -> EnvNode(ASTNode(node.value.op, LinkedList(node.value.parameters.map { pack(it) })))
                node.data.type == Identity.Var -> EnvNode(node)
                else -> node
            }
        }
    }
}