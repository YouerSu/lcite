package parser

class Lexer(private val file: String) {
    private var index: Int = 0
    private var line: Int = 1
    private var col: Int = 1
    private var peek: Char

    init {
        peek = file.first()
    }

    private fun peek(){
        if (index<file.lastIndex) {
            index += 1
            peek = file[index]
            when (peek) {
                '\n' -> {
                    line = 0
                    col++
                }
                '\r' -> line = 0
                else -> line++
            }
        }
        else peek = '\u0000'
    }

    fun getNextToken(): Token {
        delBlank()

        if (Identity.Start.isMe(peek.toString())){
            peek()
            return Token(Identity.Start,"(",line, col)
        }else {
            var tokenString = ""
            do{
                tokenString += peek
                peek()
            } while (
                isBlank(peek).not()&&
                Identity.End.isMe(peek.toString()).not()
            )
            Identity.values().forEach { if (it.isMe(tokenString)) return Token(it, tokenString, line, col) }
            error("Can't regard $tokenString as any Identity")
        }
    }

    private fun isBlank(char: Char): Boolean{
        val blank = "\t\n\r "
        return blank.contains(char)
    }

    private fun delBlank(){
        while (isBlank(peek)) peek()
        if (peek == ';') {
            while (peek != '\n') peek()
        }
    }
}