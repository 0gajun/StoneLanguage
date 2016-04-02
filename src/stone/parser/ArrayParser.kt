package stone.parser

import stone.ast.ArrayLiteral
import stone.ast.ArrayRef
import stone.parser.Parser.Companion.rule
import kotlin.system.exitProcess

/**
 * Created by Junya on 2016/04/02.
 */
open class ArrayParser: FuncParser() {
    val elements = rule(ArrayLiteral::class).ast(expr).repeat(rule().sep(",").ast(expr))

    init {
        reserved.add("]")
        primary.insertChoice(rule().sep("[").maybe(elements).sep("]"))
        postfix.insertChoice(rule(ArrayRef::class).sep("[").ast(expr).sep("]"))
    }
}
