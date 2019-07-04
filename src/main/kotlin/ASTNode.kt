import java.util.*

class ASTNode(private val procedure: Token, private val pars: LinkedList<Token>) {
    fun eval(): Any {
        val process = procedure.getValue()
        return when {
            process is ASTNode -> apply(process)
            procedure.metaType == Type.AtomicOperation -> TODO()
            else -> TODO()
        }
    }

    fun apply(procedure: ASTNode): Any {
        Env.intoEnv()
        pars.map { Env.addVar(it.value,it.getValue()) }
        val value = procedure.eval()
        pars.map { Env.removeVar(it.value) }
        Env.leftEnv()
        return value
    }

}
