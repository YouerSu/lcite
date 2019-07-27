import org.junit.Test
import parser.Identity
import parser.Lexer
import kotlin.test.assertEquals

class LexTest {

    @Test
    fun numberTest(){
        assertEquals(Identity.Int, Lexer("-233").getNextToken().identity)
        assertEquals(Identity.Long, Lexer("-233L").getNextToken().identity)
        assertEquals(Identity.Double, Lexer("-233.0e-10").getNextToken().identity)
        assertEquals(Identity.Float, Lexer("-23.3e-2F").getNextToken().identity)
    }
}