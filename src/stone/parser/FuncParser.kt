package stone.parser

import stone.ast.Arguments
import stone.ast.DefStmnt
import stone.ast.ParameterList
import stone.parser.Parser.Companion.rule

/**
 * プロシージャを追加したパーサ
 *
 * Created by Junya on 2016/03/21.
 */

open class FuncParser : BasicParser() {
    protected val param = rule().identifier(reserved)

    protected val params = rule(ParameterList::class).ast(param).repeat(rule().sep(",").ast(param))

    protected val paramList = rule().sep("(").maybe(params).sep(")")

    protected val def = rule(DefStmnt::class).sep("def").identifier(reserved).ast(paramList).ast(block)

    protected val args = rule(Arguments::class).ast(expr).repeat(rule().sep(",").ast(expr))

    protected val postfix = rule().sep("(").maybe(args).sep(")")

    init {
        reserved.add(")") // for defining function
        primary.repeat(postfix)
        simple.option(args)
        program.insertChoice(def)
    }
}
