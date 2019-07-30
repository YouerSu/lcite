package parser

import lib.*

enum class Identity {
    AtomicOperation {
        override fun isMe(str: kotlin.String): Boolean {
            return when(str){
                add, minus, multiply, divide, define, lambda, GT, EQ, LT, cond -> true
                else -> false
            }
        }

        override fun toValue(str: kotlin.String): Operation {
            return when(str){
                add -> Add()
                minus -> Minus()
                multiply -> Multiply()
                divide -> Divide()
                define -> Define()
                lambda -> Lambda()
                GT -> GT()
                EQ -> EQ()
                LT -> LT()
                cond -> Cond()
                else -> error("$str isn't a atomic operation")
            }
        }
    },
    Int {
        override fun isMe(str: kotlin.String): Boolean {
            var index = 0
            if (str.firstOrNull() == '-'){
                index += 1
            }
            str.drop(index).forEach { if ((it in parser.Number).not()) return false}
            return true
        }

        override fun toValue(str: kotlin.String): kotlin.Int = str.toInt()
    },
    Long {
        override fun isMe(str: kotlin.String): Boolean {
            if (Int.isMe(str.dropLast(1))){
                return str.last() == 'L'||str.last() == 'l'
            }else {
                return false
            }
        }

        override fun toValue(str: kotlin.String): kotlin.Long = str.toLong()
    },
    Double {
        override fun isMe(str: kotlin.String): Boolean {
            var count = 0
            str.forEach {
                val double = parser.Number+'E'+'e'+'+'+'-'
                if (it == '.') count++
                else if ((it in double).not()) return false
            }
            return count == 1
        }

        override fun toValue(str: kotlin.String): kotlin.Double = str.toDouble()
    },
    Float {
        override fun isMe(str: kotlin.String): Boolean {
            if (Double.isMe(str.dropLast(1))){
                return str.last() == 'F'||str.last() == 'f'
            }else {
                return false
            }
        }

        override fun toValue(str: kotlin.String): kotlin.Float = str.toFloat()
    },
    Char {
        override fun isMe(str: kotlin.String): Boolean {
            return str.length == 3&&str[0] == '#'&&str[1] == '\\'
        }

        override fun toValue(str: kotlin.String): kotlin.Char = str[2]
    },
    Var {
        override fun isMe(str: kotlin.String): Boolean {
            str.forEach { if ((it in parser.Var).not()) return false}
            return true
        }

        override fun toValue(str: kotlin.String): Any = str
    },
    String {
        override fun isMe(str: kotlin.String): Boolean {
            return str.firstOrNull() == '"'&&str.last() == '"'
        }

        override fun toValue(str: kotlin.String): kotlin.String{
            return str.drop(1).dropLast(1)
        }
    },
    Start {
        override fun isMe(str: kotlin.String): Boolean {
            return str == "("
        }
    },
    End {
        override fun isMe(str: kotlin.String): Boolean {
            return str == ")"
        }
    },
    Escape{
        override fun isMe(str: kotlin.String): Boolean = str == "'"

        override fun toValue(str: kotlin.String): Any = Escape
    },
    EOF {
        override fun isMe(str: kotlin.String): Boolean = str.firstOrNull() == '\u0000'
    },
    Number,
    Procedure,
    Cons,
    Bool,
    Nil,
    Invoke;

    open fun isMe(str: kotlin.String): Boolean = false
    open fun toValue(str: kotlin.String): Any = this
}


val Number = '0'..'9'
val Upper = 'A'..'Z'
val Lower =  'a'..'z'
val Var = Upper + Lower + '_'

const val add = "+"
const val minus = "-"
const val multiply = "*"
const val divide = "/"
const val define = "def"
const val lambda = "func"
const val GT = ">"
const val EQ = "="
const val LT = "<"
const val cond = "cond"