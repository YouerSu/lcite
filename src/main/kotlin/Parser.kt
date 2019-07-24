import lib.Operator
import java.util.*

class Parser(private val lexer: Lexer) {

    fun parse(): ValueNode{
        val token = lexer.getNextToken()
        return when(token.identity){
            Identity.Start -> {
                val value = getNode()
                ValueNode(value.identity,value)
            }
            else -> ValueNode(token.identity,token.identity.toValue(token.value))
        }
    }

    private fun getNode(): Node {
        val linkedList = LinkedList<ValueNode>()
        val func = parse()
        while(true){
            val value = parse()
            if (value.getValueType() == Identity.End)
                return Node(func.getValueType(),func,linkedList)
            else linkedList.add(value)
        }
    }

//    private fun number(token: Token): ValueNode{
//        val value = token.value
//        fun doubleOrFloat(): ValueNode =
//            if (value.last() == 'F') ValueNode(Identity.Float,value.toFloat())
//            else ValueNode(Identity.Double,value.toDouble())
//
//        fun intOrLong(): ValueNode =
//            if (value.last() == 'L') ValueNode(Identity.Long,value.toLong())
//            else ValueNode(Identity.Int,value.toInt())
//        try {
//            value.forEach {
//                when(it){
//                    'x','X','b','B' -> return intOrLong()
//                    'e','E' -> return doubleOrFloat()
//                    'F' -> return ValueNode(Identity.Float,token.value.toFloat())
//                    'L' -> return ValueNode(Identity.Long,token.value.toLong())
//                }
//            }
//            return intOrLong()
//        }catch (e: NumberFormatException){
//            error("$value can't be regarded as a number")
//        }
//    }
//
}

fun main() {
    Operator.init()
    val source = """
        (def x 10)
        (def func 
            (fun (a b c) (+ 1 (* a b c))))
    """.trimIndent()
    print(
    Parser(Lexer(source)).parse().eval()
    )
}