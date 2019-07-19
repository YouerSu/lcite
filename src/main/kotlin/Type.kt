enum class Type {
    Number,
    Int,
    Long,
    Float,
    Double,
    Char,
    Var,
    Procedure,
    Empty,
    String,
}

val Number = '0'..'9'
val Upper = 'A'..'Z'
val Lower =  'a'..'z'
val Var = Upper + Lower + '_'
const val White = "\t\n\r "