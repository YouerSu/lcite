class Token(
    val metaType: Type,
    val value: String,
    val row: Short,
    val cow: Short
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