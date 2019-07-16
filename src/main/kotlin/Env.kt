import kotlin.collections.HashMap

class Env {
    val env = HashMap<Pair<String,Int>,ValueNode>()

    companion object{
        class AtomicOperation(val procedure :(List<Any>) -> Any)
        private val env = Env().env
        private var scope: Int = 0
        fun leftEnv(){ scope -= 1 }
        fun intoEnv(){ scope += 1 }

        fun lookUp(key: String): ValueNode{
            for (nowEnv in scope downTo 0) env[Pair(key, nowEnv)]?.let { return it}
            error("Can't find $key")
        }

        fun bind(key: String, value: ValueNode){
            env[Pair(key, scope)] = value
        }

        fun untied(key: String){
            env.remove(Pair(key, scope))
        }

        private fun initAtomciOperation(){
            fun bind(key: String, operation: AtomicOperation){
                Companion.bind(key,ValueNode(Type.Procedure,operation))
            }
            bind("+",
                AtomicOperation(fun(list: List<Any>) = (list as List<Int>).sum()))
            bind("-",
                AtomicOperation(fun(list: List<Any>) = (list as List<Int>).reduce(fun(a: Int,b: Int) = a - b)))
            bind("*",
                AtomicOperation(fun(list: List<Any>) = (list as List<Int>).reduce(fun(a: Int,b: Int) = a * b)))
            bind("/",
                AtomicOperation(fun(list: List<Any>) = (list as List<Int>).reduce(fun(a: Int,b: Int) = a / b)))
        }

    }

}