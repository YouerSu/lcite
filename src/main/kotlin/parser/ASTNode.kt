package parser

import lib.Operation
import java.util.*

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
        return body.procedure(parameters)
    }

    override fun bind(values: LinkedList<ValueNode>) {
        parameters = values
    }
}
class CombintorRootNode(val body: ValueNode,override var parameters: LinkedList<ValueNode>):RootNode(body.data.line, body.data.col){


    override fun eval(): Any {
        return body.eval()
    }

    override fun bind(values: LinkedList<ValueNode>) {
        if (values.size != parameters.size){
            error("Wrong parameter table")
        }else {
            for (index in 0..parameters.lastIndex){
                Env.bind(parameters[index].value.toString(),values[index])
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
        if (values.size != parameters.size){
            error("Wrong parameter table")
        }else {
            for (index in 0..parameters.lastIndex){
                Env.bind(parameters[index].value.toString(),values[index])
            }
        }
    }
}

class ValueNode(val value: Any,data: Data): Node(data){
    override fun eval(): Any =
        when{
            value is ValueNode -> value.eval()
            value is ASTNode -> value.eval()
            value is ArrayNode -> value.eval()
            data.type == Identity.Var -> Env.lookUp(value as String).eval()
            else -> value
        }

    fun getValueType(): Identity =
        if (data.type == Identity.Var) Env.lookUp(value as String).getValueType()
        else data.type


}

class ArrayNode(val values: LinkedList<ValueNode>,data: Data): Node(data){
    override fun eval(): LinkedList<ValueNode> {
        return values
    }
}