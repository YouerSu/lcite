package lib

import parser.*
import java.util.*

abstract class Operation {
    abstract fun procedure(values: LinkedList<ValueNode>): Any
}

class Define: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Unit {
        Env.bind(
            values.first.value as String,
            values.last
        )
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