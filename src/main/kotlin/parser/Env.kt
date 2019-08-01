package parser

import java.util.*
import kotlin.collections.HashMap

class Env {

    val env = HashMap<Pair<String,Int>, ValueNode>()

    companion object{

        private val env = Env().env
        internal var deepOfEnv = 0
        internal var toDeep = -1
        internal var nowDeep = 0
        private val copy = Stack<Pair<String,ValueNode?>>()

        fun toEnv(deep: Int){
            if (deep <= nowDeep) {
                deepOfEnv = nowDeep
                toDeep = deep
                nowDeep = deep
            }else {
                //here is unreachable
                error("Out of Env")
            }
        }

        fun outEnv(toDeep: Int, nowDeep: Int, deepOfEnv: Int) {
            this.toDeep = toDeep
            this.nowDeep = nowDeep
            this.deepOfEnv = deepOfEnv
        }

        fun intoEnv(){
            if (toDeep < 0){
                deepOfEnv += 1
                nowDeep = deepOfEnv
            }else {
                copy.add(Pair("NextEnv",null))
                nowDeep += 1
            }
        }

        fun leftEnv(){
            if (toDeep < 0){
                deepOfEnv -= 1
                nowDeep = deepOfEnv
            }else {
                while(copy.isEmpty().not()){
                    val elem = copy.pop()
                    if (elem.second is ValueNode){
                        env[Pair(elem.first,nowDeep)] = elem.second!!
                    }else {
                        break
                    }
                }
                if (nowDeep-1 <= toDeep){
                    nowDeep -= 1
                }else {
                    //here is unreachable
                    error("Out of Env")
                }
            }
        }

        fun lookUp(key: String): ValueNode{
            for (deep in nowDeep downTo 0) {
                env[Pair(key, deep)]?.let { return it }
            }
            error("Out of bound: $key")
        }

        fun bind(key: String, value: ValueNode){
            if (toDeep >= 0){
                env[Pair(key, nowDeep)]?.let { copy.push(Pair(key,it)) }
            }
            env[Pair(key, nowDeep)] = value
        }

        fun untied(key: String){
            env.remove(Pair(key, nowDeep))
        }

    }

}