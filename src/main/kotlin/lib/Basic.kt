package lib

import Env
import Identity
import Node
import ValueNode
import FuncNode
import check
import java.util.*

fun Basic.import(name: String, identity: Identity){
    Env.bind(name, ValueNode(Identity.Procedure,
        FuncNode(identity,ValueNode(Identity.Procedure,this),LinkedList())))
}

fun bindOp(name: String, identity: Identity, procedure: (LinkedList<ValueNode>) -> Any){
    AtomicOperation(procedure).import(name,identity)
}

abstract class Basic {
    abstract fun procedure(values: LinkedList<ValueNode>): Any
}

class AtomicOperation(val operator :(LinkedList<ValueNode>) -> Any): Basic() {
    override fun procedure(values: LinkedList<ValueNode>): Any = operator(values)
}

class Operator{
    companion object {

        private fun <T: Number>LinkedList<ValueNode>.toNumbers(): List<T> = this.map { it.eval() as T }

        private fun add(values: LinkedList<ValueNode>): Any =
            when(values.first.getValueType()){
                Identity.Int -> values.toNumbers<Int>().sum()
                Identity.Long -> values.toNumbers<Long>().sum()
                Identity.Float -> values.toNumbers<Float>().sum()
                Identity.Double -> values.toNumbers<Double>().sum()
                else -> error("")
            }//result::class = Double|Long|Float|Double

        private fun minus(values: LinkedList<ValueNode>) =
            when(values.first.getValueType()){
                Identity.Int -> values.toNumbers<Int>().reduce( fun(a: Int, b: Int) = a-b)
                Identity.Long -> values.toNumbers<Long>().reduce( fun(a: Long, b: Long) = a-b)
                Identity.Float -> values.toNumbers<Float>().reduce( fun(a: Float, b: Float) = a-b)
                Identity.Double -> values.toNumbers<Double>().reduce( fun(a: Double, b: Double) = a-b)
                else -> error("")
            }//result::class = Double|Long|Float|Double

        private fun multiply(values: LinkedList<ValueNode>) =
            when(values.first.getValueType()){
                Identity.Int -> values.toNumbers<Int>().reduce( fun(a: Int, b: Int) = a*b)
                Identity.Long -> values.toNumbers<Long>().reduce( fun(a: Long, b: Long) = a*b)
                Identity.Float -> values.toNumbers<Float>().reduce( fun(a: Float, b: Float) = a*b)
                Identity.Double -> values.toNumbers<Double>().reduce( fun(a: Double, b: Double) = a*b)
                else -> error("")
            }//result::class = Double|Long|Float|Double

        private fun divide(values: LinkedList<ValueNode>) =
            when(values.first.getValueType()){
                Identity.Int -> values.toNumbers<Int>().reduce( fun(a: Int, b: Int) = a/b)
                Identity.Long -> values.toNumbers<Long>().reduce( fun(a: Long, b: Long) = a/b)
                Identity.Float -> values.toNumbers<Float>().reduce( fun(a: Float, b: Float) = a/b)
                Identity.Double -> values.toNumbers<Double>().reduce( fun(a: Double, b: Double) = a/b)
                else -> error("")
            }//result::class = Double|Long|Float|Double

        private fun def(values: LinkedList<ValueNode>){
            val variable = values.first
            if (variable.getValueType().check(Identity.Var)) error("")
            else Env.bind(variable.value.toString(),values.last)
        }

        private fun lambda(values: LinkedList<ValueNode>): FuncNode{
            fun Node.toParameters(): LinkedList<ValueNode> =
                this.pars.let { it.addFirst(this.procedure); return@let it }
            val parameter = values.first.eval()
            val body = values.last.eval()
            return if (parameter is Node&&body is Node) FuncNode(body.identity,values.last,parameter.toParameters())
            else error("")
        }

        fun init(){
            bindOp("+",Identity.Number,this::add)
            bindOp("-",Identity.Number,this::minus)
            bindOp("*",Identity.Number,this::multiply)
            bindOp("/",Identity.Number,this::divide)
            bindOp(this::def.name,Identity.EOF,this::def)
            bindOp("fun",Identity.Procedure,this::lambda)
        }
    }
}