import java.util.*

abstract class AbsNode(private val type: Type){
    abstract fun eval(): Any
    open fun getType() = type
}

class Node(type: Type,private val procedure: FuncNode, private val pars: LinkedList<ValueNode>): AbsNode(type) {
    override fun eval(): Any {
        Env.intoEnv()
        procedure.binds(pars)
        val value = procedure.eval()
        procedure.vars.forEach { Env.untied(it.value.toString()) }
        Env.leftEnv()
        return value
    }

}

class FuncNode(result: Type,val body: Any,val vars: LinkedList<ValueNode>): AbsNode(result) {
    override fun eval(): Any = when(body){
                is ValueNode -> (body.eval() as FuncNode).eval()
                is Env.Companion.AtomicOperation -> body.procedure(vars.map { it.eval() })
                else -> error("Unknown grammar")
    }

    fun binds(values: LinkedList<ValueNode>) {
        for (count in 0..values.lastIndex)
            Env.bind(vars[count].value.toString(),values[count])
    }

}

class ValueNode(type: Type,val value: Any): AbsNode(type) {
    override fun eval(): Any {
        val result = when {
            value is Node -> value.eval()
            super.getType() == Type.Var -> getValueNode().eval()
            else -> value
        }
        return result
    }

    override fun getType(): Type = getValueNode().getType()

    private fun getValueNode() = if (super.getType() == Type.Var) (Env.lookUp(value.toString()) as ValueNode) else this

}