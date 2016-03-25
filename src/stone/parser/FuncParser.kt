package stone.parser

import stone.ast.Arguments
import stone.ast.DefStmnt
import stone.ast.ParameterList
import stone.parser.Parser.rule

/**
 * プロシージャを追加したパーサ
 *
 * Created by Junya on 2016/03/21.
 */

class FuncParser : BasicParser() {
    protected val param = rule().identifier(reserved)

    protected val params = rule(ParameterList::class.java).ast(param).repeat(rule().sep(",").ast(param))

    protected val paramList = rule().sep("(").maybe(params).sep(")")

    protected val def = rule(DefStmnt::class.java).sep("def").identifier(reserved).ast(paramList).ast(block)

    protected val args = rule(Arguments::class.java).ast(expr).repeat(rule().sep(",").ast(expr))

    protected val postfix = rule().sep("(").maybe(args).sep(")")

    init {
        reserved.add(")") // for defining function
        primary.repeat(postfix)
        simple.option(args)
        program.insertChoice(def)
    }
}
