package parser

import java.util.*

class Parser(private val lexer: Lexer) {

    fun parse(): Node {
        val token = lexer.getNextToken()
        return when(token.identity){
            Identity.Start -> getASTNode()
            Identity.AtomicOperation -> Identity.AtomicOperation.toValue(token.value) as ValueNode
            else -> ValueNode(token.identity.toValue(token.value),Data(token.identity,token.line,token.col))
        }
    }

    private fun getASTNode(): ASTNode {
        return ASTNode(
            getRootNode(),
            getParameters()
        )
    }

    private fun getValueNode(): ValueNode {
        return when (val value = parse()) {
            is ValueNode -> value
            is ASTNode -> ValueNode(value, value.data)
            else -> error("")
        }
    }

    private fun getParameters(): LinkedList<ValueNode> {
        val values: LinkedList<ValueNode> = LinkedList()
        while(true){
            val value = getValueNode()
            when {
                value.data.type == Identity.End -> return values
                value.data.type == Identity.EOF -> value.data.DataError("lack a right parentheses")
                else -> values.add(value)
            }
        }
    }

    private fun getRootNode(): RootNode {
        when (val root = parse()) {
            is ValueNode -> when {
                root.data.type == Identity.AtomicOperation -> return root.eval() as AtomicRootNode
                root.data.type == Identity.Var -> return UnSolveRootNode(root,root.data)
                else -> root.data.DataError("${root.value} isn't a procedure")
            }
            is ASTNode -> return UnSolveRootNode(
                ValueNode(root,root.data),
                root.data
            )
            else -> root.data.DataError("")
        }
    }

//    private fun number(token: parser.Token): parser.ValueNode{
//        val value = token.value
//        fun doubleOrFloat(): parser.ValueNode =
//            if (value.last() == 'F') parser.ValueNode(parser.Identity.Float,value.toFloat())
//            else parser.ValueNode(parser.Identity.Double,value.toDouble())
//
//        fun intOrLong(): parser.ValueNode =
//            if (value.last() == 'L') parser.ValueNode(parser.Identity.Long,value.toLong())
//            else parser.ValueNode(parser.Identity.Int,value.toInt())
//        try {
//            value.forEach {
//                when(it){
//                    'x','X','b','B' -> return intOrLong()
//                    'e','E' -> return doubleOrFloat()
//                    'F' -> return parser.ValueNode(parser.Identity.Float,token.value.toFloat())
//                    'L' -> return parser.ValueNode(parser.Identity.Long,token.value.toLong())
//                }
//            }
//            return intOrLong()
//        }catch (e: NumberFormatException){
//            DataError("$value can't be regarded as a number")
//        }
//    }
//
}