import lib.Add
import lib.Define
import lib.Lambda
import org.junit.Test
import parser.*
import java.util.*
import kotlin.test.assertEquals

internal class AtomicOpTest(){

    @Test
    fun define(){
        val values =
            LinkedList(
                listOf(
                    ValueNode("var", Data(Identity.Var,-1,-1)),
                    ValueNode(13, Data(Identity.Int,-1,-1))
                )
            )
        Define().procedure(values)
        assertEquals(13, Env.lookUp("var").eval())
    }

    @Test
    fun lambda(){
        val paraName = ValueNode(
            LinkedList(
                listOf(
                    ValueNode("x", Data(Identity.Var,-1,-1)),
                    ValueNode("y", Data(Identity.Var,-1,-1))
                )
            ),
            Data(Identity.Cons,-1,-1)
        )
        val body = ASTNode(
            AtomicRootNode(Add(),Data(Identity.AtomicOperation,-1,-1)),
            LinkedList(listOf(
                ValueNode("x", Data(Identity.Var,-1,-1)),
                ValueNode("y", Data(Identity.Var,-1,-1))
            ))
        )
        val values =
            LinkedList(
                listOf(
                    ValueNode(paraName,paraName.data),
                    ValueNode(body,body.data)
                )
            )
        val stam =
            LinkedList(
                listOf(
                    ValueNode(13, Data(Identity.Int,-1,-1)),
                    ValueNode(10, Data(Identity.Int,-1,-1))
                )
            )
        val func = Lambda().procedure(values)
        func.bind(stam)
        assertEquals(23,func.eval())
    }

    @Test
    fun condTest(){
        val sourceCode = """
            (cond
                '((> 2 1) 1)
                '((else) 0)
            )
        """.trimIndent()
        assertEquals(1,Parser(Lexer(sourceCode)).parse().eval())
    }

}