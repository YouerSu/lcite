import org.junit.Test
import parser.Lexer
import parser.Parser
import kotlin.test.assertEquals

class Code {

    @Test
    fun test1(){
        val sourceCode = """
            (def afunc 
                (func '(x y z)
                    (cond
                        '((> x y) (+ (- x y) z))
                        '((> y z) (+ (- y z) x))
                        '(else (+ x y z))
                    )
                )
            )
            
            (afunc 1 3 2)
        """.trimIndent()

        val parser = Parser(Lexer(sourceCode))
        parser.parse().eval()
        assertEquals(2,parser.parse().eval())
    }
}