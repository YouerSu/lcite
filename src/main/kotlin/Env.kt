class Env() {
    val env = HashMap<Pair<String,Int>,Any>()

    companion object{
        val scope: Short = 0
        val env = Env()

        fun getValue(key: String): Any{
            for (nowEnv in scope downTo 0) env.env[Pair(key, nowEnv)]?.let { return it}
            error("Can't find $key")
        }
    }

}