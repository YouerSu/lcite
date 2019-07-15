enum class Symbol{
    Start,
    End,
    Operation,
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