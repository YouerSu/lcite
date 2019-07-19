enum class Symbol{
    Start,
    End,
    Number,
    String,
    Var,
    EOF,
}

class Token(
    val symbol: Symbol,
    val value: String,
    val row: Short,
    val col: Short
) {}