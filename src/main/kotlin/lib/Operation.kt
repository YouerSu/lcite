package lib

import parser.*
import java.util.*

abstract class Operation {
    abstract fun procedure(values: LinkedList<ValueNode>): Any
}

class Define: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Unit {
        Env.bind(
            getVarName(values.first),
            values.last
        )
    }

    private fun getVarName(node: ValueNode): String {
        return when(node){
            is EnvNode -> (node.value as ValueNode).value as String
            is ValueNode -> node.value as String
            else -> node.data.DataError("Isn't a var")
        }
    }
}

class Lambda: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): CombintorRootNode {
        return CombintorRootNode(
            values.last,
            values.first.eval() as LinkedList<ValueNode>
        )
    }
}