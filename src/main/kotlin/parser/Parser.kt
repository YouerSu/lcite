package parser

import lib.Operation
import java.util.*

class Parser(private val lexer: Lexer) {

    fun parse(): Node {
        val token = lexer.getNextToken()
        return when(token.identity){
            Identity.Start -> getASTNode()
            Identity.AtomicOperation -> AtomicRootNode(Identity.AtomicOperation.toValue(token.value) as Operation,Data(token.identity,token.line,token.col))
            Identity.Escape -> eParse()
            else -> ValueNode(token.identity.toValue(token.value),Data(token.identity,token.line,token.col))
        }
    }

    private fun eParse(): Node {
        val token = lexer.getNextToken()
        return when(token.identity){
            Identity.Start -> ArrayNode(
                getParameters(),
                Data(Identity.Cons,token.line,token.col)
            )
            else -> error("Can't escape the ${token.value}")
        }
    }

    private fun getASTNode(): ASTNode {
        return ASTNode(
            getRootNode(),
            getParameters()
        )
    }

    private fun getValueNode(): ValueNode {
        return when (val value = parse()) {
            is ValueNode -> value
            else -> ValueNode(value, value.data)
        }
    }

    private fun getParameters(): LinkedList<ValueNode> {
        val values: LinkedList<ValueNode> = LinkedList()
        while(true){
            val value = getValueNode()
            when {
                value.data.type == Identity.End -> return values
                value.data.type == Identity.EOF -> value.data.DataError("lack a right parentheses")
                else -> values.add(value)
            }
        }
    }

    private fun getRootNode(): RootNode {
        when (val root = parse()) {
            is ValueNode -> when {
                root.data.type == Identity.AtomicOperation -> return root.eval() as AtomicRootNode
                root.data.type == Identity.Var -> return UnSolveRootNode(root,root.data)
                else -> root.data.DataError("${root.value} isn't a procedure")
            }
            is ASTNode -> return UnSolveRootNode(
                ValueNode(root,root.data),
                root.data
            )
            else -> root.data.DataError("")
        }
    }
}