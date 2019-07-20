import java.util.*

fun Basic.import(name: String,type: Type){
    Env.bind(name,ValueNode(type,this))
}

abstract class Basic {
    abstract fun procedure(values: LinkedList<ValueNode>): ValueNode
}

class AtomicOperation(val procedure :(LinkedList<ValueNode>) -> ValueNode): Basic() {
    override fun procedure(values: LinkedList<ValueNode>): ValueNode = procedure(values)

    companion object{
        fun createAtomicOreration(name: String,type: Type,procedure: (LinkedList<ValueNode>) -> ValueNode){
            AtomicOperation(procedure).import(name,type)
        }
    }

}

class Operator{
    companion object {

        fun <T: Number>LinkedList<ValueNode>.toNumbers(): List<T> = this.map { it.eval() as T }

        fun Add(values: LinkedList<ValueNode>): ValueNode {
            val type = values.first.getValueType()
            val result = when(type){
                Type.Int -> values.toNumbers<Int>().sum()
                Type.Long -> values.toNumbers<Long>().sum()
                Type.Float -> values.toNumbers<Float>().sum()
                Type.Double -> values.toNumbers<Double>().sum()
                else -> error("")
            }//result::class = Double|Long|Float|Double
            return ValueNode(type,result)
        }

        fun Miuns(values: LinkedList<ValueNode>): ValueNode {
            val type = values.first.getValueType()
            val result = when(type){
                Type.Int -> values.toNumbers<Int>().reduce( fun(a: Int,b: Int) = a-b)
                Type.Long -> values.toNumbers<Long>().reduce( fun(a: Long,b: Long) = a-b)
                Type.Float -> values.toNumbers<Float>().reduce( fun(a: Float,b: Float) = a-b)
                Type.Double -> values.toNumbers<Double>().reduce( fun(a: Double,b: Double) = a-b)
                else -> error("")
            }//result::class = Double|Long|Float|Double
            return ValueNode(type,result)
        }

        fun Multiply(values: LinkedList<ValueNode>): ValueNode {
            val type = values.first.getValueType()
            val result = when(type){
                Type.Int -> values.toNumbers<Int>().reduce( fun(a: Int,b: Int) = a*b)
                Type.Long -> values.toNumbers<Long>().reduce( fun(a: Long,b: Long) = a*b)
                Type.Float -> values.toNumbers<Float>().reduce( fun(a: Float,b: Float) = a*b)
                Type.Double -> values.toNumbers<Double>().reduce( fun(a: Double,b: Double) = a*b)
                else -> error("")
            }//result::class = Double|Long|Float|Double
            return ValueNode(type,result)
        }

        fun Divide(values: LinkedList<ValueNode>): ValueNode {
            val type = values.first.getValueType()
            val result = when(type){
                Type.Int -> values.toNumbers<Int>().reduce( fun(a: Int,b: Int) = a/b)
                Type.Long -> values.toNumbers<Long>().reduce( fun(a: Long,b: Long) = a/b)
                Type.Float -> values.toNumbers<Float>().reduce( fun(a: Float,b: Float) = a/b)
                Type.Double -> values.toNumbers<Double>().reduce( fun(a: Double,b: Double) = a/b)
                else -> error("")
            }//result::class = Double|Long|Float|Double
            return ValueNode(type,result)
        }
    }
}