package lib

import parser.*
import java.util.*

abstract class Operation {
    abstract fun procedure(values: LinkedList<ValueNode>): Any
}

class Define: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): Unit {
        Env.bind(
            values.first.value as String,
            values.last
        )
    }
}

class Lambda: Operation(){
    override fun procedure(values: LinkedList<ValueNode>): CombintorRootNode {
        return CombintorRootNode(
            values.last,
            (values.first.eval() as ArrayNode).eval(),
            values.last.data
        )
    }
}

abstract class NumberOp: Operation(){
    companion object{
        fun <T: Number>toNumbers(numbers: LinkedList<ValueNode>): List<T> {
            return numbers.map { it.eval() as T }
        }
    }
}

class Add: NumberOp() {
    override fun procedure(values: LinkedList<ValueNode>): Any {
        return when (values.first.getValueType()) {
            Identity.Int -> toNumbers<Int>(values).sum()
            Identity.Long -> toNumbers<Long>(values).sum()
            Identity.Float -> toNumbers<Float>(values).sum()
            Identity.Double -> toNumbers<Double>(values).sum()
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Minus: NumberOp() {
    override fun procedure(values: LinkedList<ValueNode>): Any {
        return when (values.first.getValueType()) {
            Identity.Int -> toNumbers<Int>(values).reduce(fun(a: Int, b: Int) = a - b)
            Identity.Long -> toNumbers<Long>(values).reduce(fun(a: Long, b: Long) = a - b)
            Identity.Float -> toNumbers<Float>(values).reduce(fun(a: Float, b: Float) = a - b)
            Identity.Double -> toNumbers<Double>(values).reduce(fun(a: Double, b: Double) = a - b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Multiply: NumberOp() {
    override fun procedure(values: LinkedList<ValueNode>): Any {
        return when (values.first.getValueType()) {
            Identity.Int -> toNumbers<Int>(values).reduce(fun(a: Int, b: Int) = a * b)
            Identity.Long -> toNumbers<Long>(values).reduce(fun(a: Long, b: Long) = a * b)
            Identity.Float -> toNumbers<Float>(values).reduce(fun(a: Float, b: Float) = a * b)
            Identity.Double -> toNumbers<Double>(values).reduce(fun(a: Double, b: Double) = a * b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Divide: NumberOp() {
    override fun procedure(values: LinkedList<ValueNode>): Any {
        return when (values.first.getValueType()) {
            Identity.Int -> toNumbers<Int>(values).reduce(fun(a: Int, b: Int) = a / b)
            Identity.Long -> toNumbers<Long>(values).reduce(fun(a: Long, b: Long) = a / b)
            Identity.Float -> toNumbers<Float>(values).reduce(fun(a: Float, b: Float) = a / b)
            Identity.Double -> toNumbers<Double>(values).reduce(fun(a: Double, b: Double) = a / b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

abstract class BoolOp: NumberOp()

class Ord: BoolOp(){
    override fun procedure(values: LinkedList<ValueNode>): Int {
        return (values[0].eval() as Comparable<Any>).compareTo(values[1].eval())
    }
}

class Cond: BoolOp(){
    override fun procedure(values: LinkedList<ValueNode>): Any {
        Env.bind("else", ValueNode(true,Data(Identity.Bool,-1,-1)))
        values.map { it.eval() as ASTNode }.forEach{ if (it.op.eval() as Boolean) return it.parameters.last.eval()}
        return Identity.Nil
    }
}


fun main() {
    print(
    Ord().procedure(
        LinkedList(
            listOf(
                ValueNode(12, Data(Identity.Int,0,0)),
                ValueNode(13, Data(Identity.Int,0,0))
            )
        )
    ))
}