package lib

import parser.ASTNode
import parser.Setting
import parser.ValueNode
import java.util.*

class Setting: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Unit {
        when(values.first.eval() as String){
            "call-by-name" -> Setting.CALL_BY_NAME.bool = values.last.eval() as Boolean
        }
    }
}