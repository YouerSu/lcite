enum class Type {
    Int,
    Char,
    Var,
    Procedure
}

fun type(token: String): Type =
    when(token.first()){
        in '1'..'9' -> number(token)
        '-' -> {
            if (token.lastIndex == 0) func(token)
            else number(token)
        }
        else -> TODO()
    }

fun func(token: String): Type {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    return Type.Procedure
}


fun number(token: String): Type {
    TODO()
    return Type.Int
}
