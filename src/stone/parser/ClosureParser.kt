package stone.parser

import stone.ast.Fun
import stone.parser.Parser.rule

/**
 * クロージャに対応したparser
 *
 * Created by Junya on 2016/03/26.
 */
class ClosureParser : FuncParser() {
    private val closure = rule(Fun::class.java).sep("fun").ast(paramList).ast(block)
    init {
        primary.insertChoice(closure)
    }
}
