enum class Symbol{
    Start,
    End,
    Number,
    String,
    Var,
    EOL,
}

class Token(
    val symbol: Symbol,
    val value: String,
    val row: Short,
    val col: Short
) {}