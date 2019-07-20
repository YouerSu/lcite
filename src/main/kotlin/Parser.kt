import java.lang.NumberFormatException
import java.util.*

class Parser(private val lexer: Lexer) {

    fun parse(): ValueNode{
        val token = lexer.getNextToken()
        return when(token.symbol){
            Symbol.Start -> {
                val value = getNode()
                ValueNode(value.type,value)
            }
            Symbol.End -> ValueNode(Type.Empty,Symbol.End)
            Symbol.Number -> number(token)
            Symbol.String -> ValueNode(Type.String,token.value)
            Symbol.Var -> ValueNode(Type.Var,token.value)
            Symbol.EOF -> ValueNode(Type.Empty,Symbol.EOF)
        }
    }

    private fun getNode(): Node {
        val linkedList = LinkedList<ValueNode>()
        val func = parse()
        while(true){
            val value = parse()
            if (value.getValueType() == Type.Empty)
                return Node(func.getValueType(),func,linkedList)
            else linkedList.add(value)
        }
    }

    private fun number(token: Token): ValueNode{
        val value = token.value
        fun doubleOrFloat(): ValueNode =
            if (value.last() == 'F') ValueNode(Type.Float,value.toFloat())
            else ValueNode(Type.Double,value.toDouble())

        fun intOrLong(): ValueNode =
            if (value.last() == 'L') ValueNode(Type.Long,value.toLong())
            else ValueNode(Type.Double,value.toInt())
        try {
            value.forEach {
                when(it){
                    'x','X','b','B' -> return intOrLong()
                    'e','E' -> return doubleOrFloat()
                    'F' -> return ValueNode(Type.Float,token.value.toFloat())
                    'L' -> return ValueNode(Type.Long,token.value.toLong())
                }
            }
            return intOrLong()
        }catch (e: NumberFormatException){
            error("$value can't be regarded as a number")
        }
    }

}