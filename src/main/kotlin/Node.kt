import java.util.*

class Node(val type: Type,private val procedure: ValueNode, private val pars: LinkedList<ValueNode>) {
    fun eval(): Any {
        Env.intoEnv()
        val value = (procedure.eval() as FuncNode).eval(pars)
        Env.leftEnv()
        return value
    }

}

class FuncNode(result: Type, private val body: ValueNode, private val vars: LinkedList<ValueNode>) {
    fun eval(values: LinkedList<ValueNode>): Any =
        when (val func = body.eval()) {
            is ValueNode -> {
                (body.eval() as FuncNode).eval(values)
            }

            is Node -> {
                binds(values)
                func.eval()
                vars.forEach { Env.untied(it.value.toString()) }
            }

            is Env.Companion.AtomicOperation -> func.procedure(vars.map { it.eval() })

            else -> error("Unknown grammar")
        }

    private fun binds(values: LinkedList<ValueNode>) {
        for (count in 0..values.lastIndex)
            Env.bind(vars[count].value.toString(),values[count])
    }

}

class ValueNode(private val type: Type, val value: Any) {
    fun eval(): Any = when {
            value is Node -> value.eval()
            type == Type.Var -> getValueNode().eval()
            else -> value
        }

    fun getValueType(): Type = getValueNode().getValueType()

    private fun getValueNode() = if (type == Type.Var) Env.lookUp(value.toString()) else this

}