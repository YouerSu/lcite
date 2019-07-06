import java.util.*

abstract class AbsNode(val type: Type){
    abstract fun eval(): Any
}

class Node(type: Type,private val procedure: FuncNode, private val pars: LinkedList<ValueNode>): AbsNode(type) {
    override fun eval(): Any {
        Env.intoEnv()
        procedure.binds(pars)
        val value = procedure.eval()
        procedure.vars.forEach { Env.untied(it) }
        Env.leftEnv()
        return value
    }

}

class FuncNode(result: Type,val body: Any, val vars: LinkedList<String>): AbsNode(result) {
    override fun eval(): Any = when(body){
                is ValueNode -> (body.eval() as FuncNode).eval()
                is Env.Companion.AtomicOperation -> body.procedure(vars.map { Env.lookUp(it) })
                else -> error("Unknown grammar")
    }

    fun binds(values: LinkedList<ValueNode>) {
        for (count in 0..values.lastIndex)
            Env.bind(vars[count],values[count])
    }
}
class ValueNode(type: Type,val value: Any): AbsNode(type) {
    override fun eval() =
        if (value is Node) value.eval()
        else value

}