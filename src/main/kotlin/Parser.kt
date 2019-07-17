import java.util.*

class Parser(val lexer: Lexer) {

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
            Symbol.Var -> Env.lookUp(token.value)
            Symbol.EOL -> TODO()
        }
    }

    private fun getNode(): Node {
        val linkedList = LinkedList<ValueNode>()
        val func = parse()
        while(true){
            val value = parse()
            if (value.value == Type.Empty)
                return Node(func.getValueType(),func,linkedList)
            else linkedList.add(value)
        }
    }

    private fun number(token: Token): ValueNode{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}