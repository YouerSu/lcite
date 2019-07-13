import java.util.*

class Parser(val lexer: Lexer) {

    private fun parse(): ValueNode{
        lexer.whiteSpace()
        return when(lexer.getChar()){
            '(' -> {
                val node = Node(parse().getType(),getFunc(),getValue())
                ValueNode(node.getType(),node)
            }
            in '1'..'9' -> number(lexer.getNextToken())
            else -> TODO()
        }
    }

    private fun number(token: String): ValueNode {
        TODO("Check what kind of number")
    }

    private fun getValue(list: LinkedList<ValueNode> = LinkedList()): LinkedList<ValueNode> {
        lexer.whiteSpace()
        return if (lexer.getChar() == ')') {
            lexer.index++
            lexer.row++
            LinkedList()
        }else{
            list.add(parse())
            getValue(list)
        }

    }

    private fun getFunc(): FuncNode =
        when(lexer.getNextChar()){
            '(' -> {
                val func = parse()
                FuncNode(func.getType(),func,getValue())
            }
            else -> parse().eval() as FuncNode
        }

}