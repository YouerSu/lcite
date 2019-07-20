import kotlin.collections.HashMap

class Env {
    val env = HashMap<Pair<String,Int>,ValueNode>()

    companion object{

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

    }

}