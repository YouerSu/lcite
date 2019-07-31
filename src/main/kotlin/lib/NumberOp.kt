package lib

import parser.ValueNode
import java.util.*

class Add: Operation() {
    override fun procedure(values: LinkedList<ValueNode>): Number {
        val args = values.map { it.eval() as Number}
        return when (args.first()) {
            is Int -> args.map { it.toInt() }.sum()
            is Long -> args.map { it.toLong() }.sum()
            is Float -> args.map { it.toFloat() }.sum()
            is Double -> args.map { it.toDouble() }.sum()
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Minus: Operation() {
    override fun procedure(values: LinkedList<ValueNode>): Number {
        val args = values.map { it.eval() as Number}
        return when (args.first()) {
            is Int -> args.map { it.toInt() }.reduce(fun(a: Int, b: Int) = a - b)
            is Long -> args.map { it.toLong() }.reduce(fun(a: Long, b: Long) = a - b)
            is Float -> args.map { it.toFloat() }.reduce(fun(a: Float, b: Float) = a - b)
            is Double -> args.map { it.toDouble() }.reduce(fun(a: Double, b: Double) = a - b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Multiply: Operation() {
    override fun procedure(values: LinkedList<ValueNode>): Number {
        val args = values.map { it.eval() as Number}
        return when (args.first()) {
            is Int -> args.map { it.toInt() }.reduce(fun(a: Int, b: Int) = a * b)
            is Long -> args.map { it.toLong() }.reduce(fun(a: Long, b: Long) = a * b)
            is Float -> args.map { it.toFloat() }.reduce(fun(a: Float, b: Float) = a * b)
            is Double -> args.map { it.toDouble() }.reduce(fun(a: Double, b: Double) = a * b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}

class Divide: Operation() {
    override fun procedure(values: LinkedList<ValueNode>): Number {
        val args = values.map { it.eval() as Number}
        return when (args.first()) {
            is Int -> args.map { it.toInt() }.reduce(fun(a: Int, b: Int) = a / b)
            is Long -> args.map { it.toLong() }.reduce(fun(a: Long, b: Long) = a / b)
            is Float -> args.map { it.toFloat() }.reduce(fun(a: Float, b: Float) = a / b)
            is Double -> args.map { it.toDouble() }.reduce(fun(a: Double, b: Double) = a / b)
            else -> values.first.data.DataError("Isn't a number")
        }
    }
}