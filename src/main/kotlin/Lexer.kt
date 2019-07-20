class Lexer(private val file: String) {
    private var index: Int = 0
    private var row: Short = 1
    private var col: Short = 1
    private var char: Char = file.first()

    private fun next(){
        if (++index<=file.lastIndex) {
            char = file[index]
            when (char) {
                '\n' -> {
                    row = 0
                    col++
                }
                '\r' -> row = 0
                else -> row++
            }
        }
        else char = ' '
    }

    private fun whiteSpace(){
        fun white(char: Char) = index < file.lastIndex&&White.contains(char)
        while (white(char)) next()
    }

    fun getNextToken(): Token{
        fun getString(): String{
            var str = ""
            while (White.contains(char).not()&&char != ')') {
                str += char
                next()
            }
            return str
        }
        fun createToken(symbol: Symbol,str: String = getString()) = Token(symbol, str, row, col)
        if (index>file.lastIndex) return Token(Symbol.EOF,"",row,col)
        whiteSpace()
        return when(char){
            '(' -> {
                next()
                createToken(Symbol.Start,"(")
            }
            ')' -> createToken(Symbol.End,")")
            '-' -> {
                val str = getString()
                if (str.length>1) createToken(Symbol.Number)
                else createToken(Symbol.Var,str)
            }
            in Number -> createToken(Symbol.Number)
            in  Var+'+'+'*'+'/' -> createToken(Symbol.Var)
            else -> TODO()
        }
    }

}