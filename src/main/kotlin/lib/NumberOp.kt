package lib

import parser.ValueNode
import java.util.*

fun <T: Number>toNumbers(numbers: LinkedList<ValueNode>): List<T> {
    return numbers.map { it.eval() as T }
}

class Add: Operation() {
    override fun procedure(values: LinkedList<ValueNode>): Number {
        val args = values.map { it.eval() }
        return when (args.first()) {
            is Int -> toNumbers<Int>(values).sum()
            is Long -> toNumbers<Long>(values).sum()
            is Float -> toNumbers<Float>(values).sum()
            is Double -> toNumbers<Double>(values).sum()
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Minus: Operation() {
    override fun procedure(values: LinkedList<ValueNode>): Number {
        val args = values.map { it.eval() }
        return when (args.first()) {
            is Int -> toNumbers<Int>(values).reduce(fun(a: Int, b: Int) = a - b)
            is Long -> toNumbers<Long>(values).reduce(fun(a: Long, b: Long) = a - b)
            is Float -> toNumbers<Float>(values).reduce(fun(a: Float, b: Float) = a - b)
            is Double -> toNumbers<Double>(values).reduce(fun(a: Double, b: Double) = a - b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Multiply: Operation() {
    override fun procedure(values: LinkedList<ValueNode>): Number {
        val args = values.map { it.eval() }
        return when (args.first()) {
            is Int -> toNumbers<Int>(values).reduce(fun(a: Int, b: Int) = a * b)
            is Long -> toNumbers<Long>(values).reduce(fun(a: Long, b: Long) = a * b)
            is Float -> toNumbers<Float>(values).reduce(fun(a: Float, b: Float) = a * b)
            is Double -> toNumbers<Double>(values).reduce(fun(a: Double, b: Double) = a * b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Divide: Operation() {
    override fun procedure(values: LinkedList<ValueNode>): Number {
        val args = values.map { it.eval() }
        return when (args.first()) {
            is Int -> toNumbers<Int>(values).reduce(fun(a: Int, b: Int) = a / b)
            is Long -> toNumbers<Long>(values).reduce(fun(a: Long, b: Long) = a / b)
            is Float -> toNumbers<Float>(values).reduce(fun(a: Float, b: Float) = a / b)
            is Double -> toNumbers<Double>(values).reduce(fun(a: Double, b: Double) = a / b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}