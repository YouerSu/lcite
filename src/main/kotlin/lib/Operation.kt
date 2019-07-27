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
    override fun procedure(values: LinkedList<ValueNode>): Any {
        return CombintorRootNode(
            values.last,
            (values.first as ArrayNode).eval(),
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
        return when (values.first.data.type) {
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
        return when (values.first.data.type) {
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
        return when (values.first.data.type) {
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
        return when (values.first.data.type) {
            Identity.Int -> toNumbers<Int>(values).reduce(fun(a: Int, b: Int) = a / b)
            Identity.Long -> toNumbers<Long>(values).reduce(fun(a: Long, b: Long) = a / b)
            Identity.Float -> toNumbers<Float>(values).reduce(fun(a: Float, b: Float) = a / b)
            Identity.Double -> toNumbers<Double>(values).reduce(fun(a: Double, b: Double) = a / b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}