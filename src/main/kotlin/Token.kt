enum class Type {
    Int,
    Char,
    Var,
    Procedure
}

class Token(
    val metaType: Type,
    val coordinate: Pair<Int,Int>,
    val value: String
) {
    fun getValue(): Any {
        return when(metaType){
            Type.Int -> value.toInt()
            Type.Char -> value.first()
            Type.Var,
            Type.Procedure -> Env.lookUp(value)
        }
    }
}