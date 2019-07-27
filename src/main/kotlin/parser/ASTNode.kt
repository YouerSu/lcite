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

class ASTNode(val op: RootNode,val parameters: LinkedList<ValueNode>): Node(op.data){
    override fun eval(): Any {
        op.bind(parameters)
        return op.eval()
    }
}

abstract class RootNode(data: Data): Node(data){
    abstract var parameters: LinkedList<ValueNode>
    abstract fun bind(values: LinkedList<ValueNode>)
}
class AtomicRootNode(val body: Operation, data: Data): RootNode(data){
    override lateinit var parameters: LinkedList<ValueNode>

    override fun eval(): Any {
        return body.procedure(parameters)
    }

    override fun bind(values: LinkedList<ValueNode>) {
        parameters = values
    }
}
class CombintorRootNode(val body: ValueNode,override var parameters: LinkedList<ValueNode>,data: Data):RootNode(data){


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
class UnSolveRootNode(val body: ValueNode, data: Data): RootNode(data){
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
            data.type == Identity.Var -> Env.lookUp(value.toString()).eval()
            value is ASTNode -> value.eval()
            value is ValueNode -> value.eval()
            else -> value
        }
}

class ArrayNode(val values: LinkedList<ValueNode>,data: Data): Node(data){
    override fun eval(): LinkedList<ValueNode> {
        return values
    }
}

//class Node(val identity: Identity, val procedure: ValueNode, val pars: LinkedList<ValueNode>) {
//    fun eval(): Any {
//        parser.Env.intoEnv()
//        val value = (procedure.eval() as FuncNode).eval(pars)
//        parser.Env.leftEnv()
//        return value
//    }
//
//}
//
//class FuncNode(result: Identity, private val body: ValueNode, private val vars: LinkedList<ValueNode>) {
//    fun eval(values: LinkedList<ValueNode>): Any =
//        when (val func = body.eval()) {
//            is FuncNode -> {
//                func.eval(values)
//            }
//            is Node -> {
//                binds(values)
//                val result = func.eval()
//                vars.forEach { parser.Env.untied(it.value.toString()) }
//                result
//            }
//
//            is Operation -> func.procedure(values)
//
//            else -> DataError("Unknown grammar")
//        }
//
//    private fun binds(values: LinkedList<ValueNode>) {
//        for (count in 0..values.lastIndex)
//            parser.Env.bind(vars[count].value.toString(), values[count])
//    }
//
//}
//
//class ValueNode(private val identity: Identity, val value: Any) {
//    fun eval(): Any = when {
//            value is Node -> value.eval()
//            value is ValueNode -> value.eval()
//            identity == Identity.Var -> getValueNode().eval()
//            else -> value
//        }
//
//    fun getValueType(): Identity = if (identity == Identity.Var) getValueNode().getValueType() else identity
//
//    private fun getValueNode() = if (identity == Identity.Var) parser.Env.lookUp(value.toString()) else this
//
//}