class Lexer(val file: String) {
    var index: Int = 0
    var row: Short = 0
    var col: Short = 0
    var deepOfEnv: Short = 0
    var nowToken: String = ""


    fun getChar(): Char = file[index]
    fun getNextChar(): Char = file[++index]

    fun whiteSpace(){
        while (getChar() == ' '||getChar() == '\t') row++
        while (getChar() == '\r') row = 0
        while (getChar()=='\n') {
            row = 0
            col++
        }
    }

    fun isWhiteSpace(char: Char) =
        char == ' '||
        char == '\t'||
        char == '\n'||
        char == '\r'

    fun getNextToken(): String{
        nowToken = ""
        whiteSpace()
        while (isWhiteSpace(getChar()).not()) {
            nowToken.plus(getChar())
            row++
        }
        return nowToken
    }


}