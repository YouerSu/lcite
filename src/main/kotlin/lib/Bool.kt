package lib

import parser.*
import java.util.*


fun compare(values: LinkedList<ValueNode>): Int {
    return (values[0].eval() as Comparable<Any>).compareTo(values[1].eval())
}

class GT: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Boolean {
        return compare(values) == 1
    }
}

class EQ: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Any {
        return compare(values) == 0
    }
}

class LT: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Any {
        return compare(values) == -1
    }
}


class Cond: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Any {
        Env.bind("else", ValueNode(true, Data(Identity.Bool, -1, -1)))
        values.map { it.eval() as LinkedList<ValueNode> }.forEach{ if (it.first.eval() as Boolean) return it.last.eval()}
        return Identity.Nil
    }
}