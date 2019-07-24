class Lexer(private val file: String) {
    private var index: Int = 0
    private var line: Int = 1
    private var col: Int = 1
    private var char: Char

    init {
        char = file.first()
    }

    private fun nextChar(){
        if (index<file.lastIndex) {
            index += 1
            char = file[index]
            when (char) {
                '\n' -> {
                    line = 0
                    col++
                }
                '\r' -> line = 0
                else -> line++
            }
        }
        else char = '\u0000'
    }

    fun getNextToken(): Token{
        delBlank()
        val tokenString =
        if (isSingleToken(char)){
            val result = char
            nextChar()
            result.toString()
        }else {
            var str = ""
            do{
                str += char
                nextChar()
            } while (
                isBlank(char).not()&&
                isSingleToken(char).not()
            )
            str
        }
        Identity.values().forEach { if (it.isMe(tokenString)) return Token(it,tokenString,line,col) }
        error("Can't regard $tokenString as any ")
    }

    private fun isBlank(char: Char): Boolean{
        val blank = "\t\n\r "
        return blank.contains(char)
    }

    private fun delBlank(){
        while (isBlank(char)) nextChar()
    }

    private fun isSingleToken(char: Char): Boolean =
        when(char){
            '-' -> isBlank(file[index+1])
            '(',')','+','*','/' -> true
            else -> false
        }

}