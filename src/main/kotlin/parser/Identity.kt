package parser

enum class Identity {
    Start {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    End {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    Number{
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): kotlin.Number{
            TODO()
        }
    },
    Int {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): kotlin.Int = str.toInt()
    },
    Long {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): kotlin.Long = str.toLong()
    },
    Float {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): kotlin.Float = str.toFloat()
    },
    Double {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): kotlin.Double = str.toDouble()
    },
    Char {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): kotlin.Char = str.first()
    },
    Var {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): Any = str
    },
    AtomicOperation {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): ValueNode {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    Procedure {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    String {
        override fun isMe(str: kotlin.String): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toValue(str: kotlin.String): kotlin.String = str
    },
    EOF {
        override fun isMe(str: kotlin.String): Boolean = str.first() == '\u0000'

        override fun toValue(str: kotlin.String): Any = EOF
    };

    abstract fun isMe(str: kotlin.String): Boolean
    abstract fun toValue(str: kotlin.String): Any
}


val Number = '0'..'9'
val Upper = 'A'..'Z'
val Lower =  'a'..'z'
val Var = Upper + Lower + '_'
const val White = "\t\n\r "

fun isNumber(identity: Identity) = identity == Identity.Int ||identity == Identity.Long ||identity == Identity.Float ||identity == Identity.Double
fun Identity.check(identity: Identity) = this == identity