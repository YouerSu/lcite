package parser

import java.util.*
import kotlin.collections.HashMap

class Env {

    //val env = HashMap<Pair<String,Int>, ValueNode>()
    val dataEnv = HashMap<String, ValueNode>()
    val copy = Stack<Pair<String,Any?>>()

    companion object{

        private val env = Env()
        private val dataEnv = env.dataEnv
        private val copy = env.copy
        //val deep = 0

        fun intoEnv(){
            copy.add(Pair("NextEnv",null))
        }

        fun leftEnv(){
            while (copy.empty() && copy.peek().second is ValueNode){
                copy.pop().let { dataEnv[it.first] = it.second as ValueNode }
            }
        }

        fun lookUp(key: String): ValueNode {
            dataEnv[key]?.let { return it }
            error("Out of bound: $key")
        }

        fun lookUp(key: Pair<String,Int>){

        }

        fun bind(key: String, value: ValueNode){
            dataEnv[key]?.let { copy.add(Pair(key,it)) }
            dataEnv[key] = value
        }

        fun untied(key: String){
            dataEnv.remove(key)
        }

    }

}