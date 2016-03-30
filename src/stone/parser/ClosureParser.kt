package stone.parser

import stone.ast.Fun
import stone.parser.Parser.Companion.rule

/**
 * クロージャに対応したparser
 *
 * Created by Junya on 2016/03/26.
 */
open class ClosureParser : FuncParser() {
    private val closure = rule(Fun::class).sep("fun").ast(paramList).ast(block)
    init {
        primary.insertChoice(closure)
    }
}
