package stone.parser

import stone.ast.ClassBody
import stone.ast.ClassStmnt
import stone.ast.Dot
import stone.parser.Parser.Companion.rule
import stone.token.Token

/**
 * クラスに対応した構文解析器
 *
 * Created by Junya on 2016/03/30.
 */
class ClassParser(): ClosureParser() {
    val member = rule().or(def, simple)

    val classBody = rule(ClassBody::class).sep("{")
            .option(member)
            .repeat(rule().sep(";", Token.EOL).option(member))
            .sep("}")

    val defclass = rule(ClassStmnt::class).sep("class").identifier(reserved)
            .option(rule().sep("extends").identifier(reserved))
            .ast(classBody)

    val memberAccess = rule(Dot::class).sep(".").identifier(reserved)

    init {
        postfix.insertChoice(memberAccess)
        program.insertChoice(defclass)
    }
}
