class Parser(val lexer: Lexer) {

    fun parse(): ValueNode{
        val token = lexer.getNextToken()
        return when(token.symbol){
            Symbol.Start -> {
                val value = parse()
                ValueNode(value.getType(),value)
            }
            Symbol.End -> ValueNode(Type.Empty,Symbol.End)
            Symbol.Number -> number(token)
            Symbol.Operation -> func(token)
            Symbol.String -> ValueNode(Type.String,token.value)
            Symbol.Var -> Env.lookUp(token.value)
            Symbol.EOL -> TODO()
        }
    }

    private fun func(token: Token): ValueNode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun number(token: Token): ValueNode{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}