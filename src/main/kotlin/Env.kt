import kotlin.collections.HashMap

class Env {
    val env = HashMap<Pair<String,Int>,Any>()

    companion object{
        class AtomicOperation(val procedure :(List<Any>) -> Any)
        private val env = Env().env
        private var scope: Int = 0
        fun leftEnv(){ scope -= 1 }
        fun intoEnv(){ scope += 1 }

        fun lookUp(key: String): Any{
            for (nowEnv in scope downTo 0) env[Pair(key, nowEnv)]?.let { return it}
            error("Can't find $key")
        }

        fun bind(key: String, any: Any){
            env[Pair(key, scope)] = any
        }

        fun untied(key: String){
            env.remove(Pair(key, scope))
        }

        private fun initAtomciOperation(){
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