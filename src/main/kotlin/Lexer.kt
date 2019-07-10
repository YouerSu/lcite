class Lexer(val file: String) {
    var row: Short = 0
    var col: Short = 0
    var deepOfEnv: Short = 0
    var nowToken: String = ""


    fun getChar(): Char = file[row+col]

    fun whiteSpace(){
        while (getChar() == ' '||getChar() == '\t'||getChar() == '\r') row++
        while (getChar()=='\n') col++
        while (getChar() == '(') deepOfEnv++
        while (getChar() == ')') deepOfEnv--
    }

    fun isWhiteSpace(char: Char) =
        char == ' '||
        char == '\t'||
        char == '\n'||
        char == '\r'||
        char == '('||
        char == ')'

    fun getTokenType(token: String): Type{
        TODO()
    }

    fun getNextToken(): Token{
        nowToken = ""
        whiteSpace()
        while (isWhiteSpace(getChar()).not()) {
            nowToken.plus(getChar())
            row++
        }
        return Token(getTokenType(nowToken),nowToken,row,col,deepOfEnv)
    }


}