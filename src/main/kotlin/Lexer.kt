import java.io.BufferedInputStream

class Lexer(val file: BufferedInputStream) {
    var row: Short = 0
    var cow: Short = 0
    var deepOfEnv: Short = 0
    var nowToken: String = ""


    fun getNextToken(): Token{
        TODO()
    }

}