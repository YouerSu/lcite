package lib

import Env
import Type
import Node
import ValueNode
import FuncNode
import check
import java.util.*

fun Basic.import(name: String, type: Type){
    Env.bind(name, ValueNode(Type.Procedure,
        FuncNode(type,ValueNode(Type.Procedure,this),LinkedList())))
}

fun bindOp(name: String, type: Type, procedure: (LinkedList<ValueNode>) -> Any){
    AtomicOperation(procedure).import(name,type)
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
                Type.Int -> values.toNumbers<Int>().sum()
                Type.Long -> values.toNumbers<Long>().sum()
                Type.Float -> values.toNumbers<Float>().sum()
                Type.Double -> values.toNumbers<Double>().sum()
                else -> error("")
            }//result::class = Double|Long|Float|Double

        private fun minus(values: LinkedList<ValueNode>) =
            when(values.first.getValueType()){
                Type.Int -> values.toNumbers<Int>().reduce( fun(a: Int, b: Int) = a-b)
                Type.Long -> values.toNumbers<Long>().reduce( fun(a: Long, b: Long) = a-b)
                Type.Float -> values.toNumbers<Float>().reduce( fun(a: Float, b: Float) = a-b)
                Type.Double -> values.toNumbers<Double>().reduce( fun(a: Double, b: Double) = a-b)
                else -> error("")
            }//result::class = Double|Long|Float|Double

        private fun multiply(values: LinkedList<ValueNode>) =
            when(values.first.getValueType()){
                Type.Int -> values.toNumbers<Int>().reduce( fun(a: Int, b: Int) = a*b)
                Type.Long -> values.toNumbers<Long>().reduce( fun(a: Long, b: Long) = a*b)
                Type.Float -> values.toNumbers<Float>().reduce( fun(a: Float, b: Float) = a*b)
                Type.Double -> values.toNumbers<Double>().reduce( fun(a: Double, b: Double) = a*b)
                else -> error("")
            }//result::class = Double|Long|Float|Double

        private fun divide(values: LinkedList<ValueNode>) =
            when(values.first.getValueType()){
                Type.Int -> values.toNumbers<Int>().reduce( fun(a: Int, b: Int) = a/b)
                Type.Long -> values.toNumbers<Long>().reduce( fun(a: Long, b: Long) = a/b)
                Type.Float -> values.toNumbers<Float>().reduce( fun(a: Float, b: Float) = a/b)
                Type.Double -> values.toNumbers<Double>().reduce( fun(a: Double, b: Double) = a/b)
                else -> error("")
            }//result::class = Double|Long|Float|Double

        private fun def(values: LinkedList<ValueNode>){
            val variable = values.first
            if (variable.getValueType().check(Type.Var)) error("")
            else Env.bind(variable.value.toString(),values.last)
        }

        private fun lambda(values: LinkedList<ValueNode>): FuncNode{
            fun Node.toParameters(): LinkedList<ValueNode> =
                this.pars.let { it.addFirst(this.procedure); return@let it }
            val parameter = values.first.eval()
            val body = values.last.eval()
            return if (parameter is Node&&body is Node) FuncNode(body.type,values.last,parameter.toParameters())
            else error("")
        }

        fun init(){
            bindOp("+",Type.Number,this::add)
            bindOp("-",Type.Number,this::minus)
            bindOp("*",Type.Number,this::multiply)
            bindOp("/",Type.Number,this::divide)
            bindOp(this::def.name,Type.Empty,this::def)
            bindOp("fun",Type.Procedure,this::lambda)
        }
    }
}