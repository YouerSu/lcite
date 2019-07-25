package parser

import parser.Identity

class Token(
    val identity: Identity,
    val value: String,
    val line: Int,
    val col: Int
) {}