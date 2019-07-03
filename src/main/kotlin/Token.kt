enum class Type {
    Int,
    Char,
    Combination,
    Var,
    Procedure
}

class Token(
    val metaType: Type,
    val coordinate: Pair<Int,Int>,
    val value: String
): Eval() {
    override fun eval(): Any {
        return when(metaType){
            Type.Int -> value.toInt()
            Type.Char -> value.first()
            Type.Combination -> TODO()
            Type.Var -> TODO()
            Type.Procedure -> TODO()
        }
    }
}