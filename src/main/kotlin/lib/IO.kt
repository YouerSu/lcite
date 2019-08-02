package lib

import parser.ValueNode
import java.util.*

class Print: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Unit {
         values.forEach { print(it.eval()) }
    }
}