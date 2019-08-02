package lib

import parser.ValueNode
import java.util.*

class Head: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Any {
        if (values.size == 1){
            return (values.first.eval() as LinkedList<ValueNode>).first.eval()
        }else{
            values.first.data.DataError("Wrong args")
        }
    }
}

class Tail: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Any {
        if (values.size == 1){
            return LinkedList((values.first.eval() as LinkedList<ValueNode>).drop(1))
        }else{
            values.first.data.DataError("Wrong args")
        }
    }
}