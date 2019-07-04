class Env() {
    val env = HashMap<Pair<String,Int>,Any>()

    companion object{
        private val env = Env().env
        private var scope: Int = 0
        fun leftEnv(){ scope -= 1 }
        fun intoEnv(){ scope += 1 }

        fun lookUp(key: String): Any{
            for (nowEnv in scope downTo 0) env[Pair(key, nowEnv)]?.let { return it}
            error("Can't find $key")
        }

        fun addVar(key: String,any: Any){
            env[Pair(key, scope)] = any
        }

        fun removeVar(key: String){
            env.remove(Pair(key, scope))
        }

    }

}