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

    @Test
    fun recursive(){
        val sourceCode =
            """
                (def fibNext 
                    (func '(a b count)
                        (cond 
                            '((< count 1) b)
                            '(else (fibNext b (+ a b) (- count 1)))
                        )
                    )
                )
                
                (fibNext 1 1 5)
            """.trimIndent()
        val parser = Parser(Lexer(sourceCode))
        parser.parse().eval()
        assertEquals(13,parser.parse().eval())
    }

    @Test
    fun useLambda(){
        val sourceCode =
            """
                (def makeFunc 
                    (func '(a b c)
                        (a b c)
                    )
                )
                
                (makeFunc + 1 2)
            """.trimIndent()
        val parser = Parser(Lexer(sourceCode))
        parser.parse().eval()
        assertEquals(3,parser.parse().eval())

    }

}