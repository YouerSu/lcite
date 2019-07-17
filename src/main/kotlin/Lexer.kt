val Upper = 'A'..'Z'
val Lower =  'a'..'z'
val Var = Upper+Lower+'_'
const val White = "\t\n\r "
class Lexer(val file: String) {
    private var index: Int = 0
    private var row: Short = 0
    private var col: Short = 0
    private var char: Char = ' '

    private fun next(){
        if (index<file.lastIndex) {
            index++
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

    private fun separation(char: Char) = index >= file.length||char in White

    private fun whiteSpace(){
        while (separation(char)) next()
    }

    fun getNextToken(): Token{
        fun getString(): String{
            var str = ""
            while (separation(char)) {
                str += char
                next()
            }
            return str
        }
        fun createToken(symbol: Symbol,str: String = getString()) = Token(symbol, str, row, col)
        whiteSpace()
        return when(char){
            '(' -> createToken(Symbol.Start)
            ')' -> createToken(Symbol.End)
            '-' -> {
                val str = getString()
                if (str.length>1) createToken(Symbol.Number)
                else createToken(Symbol.Var,str)
            }
            in '0'..'9' -> createToken(Symbol.Number)
            in  Var -> createToken(Symbol.Var)
            else -> TODO()
        }
    }

}