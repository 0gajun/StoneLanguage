package stone.chapters.chap6

import stone.ast.NullStmnt
import stone.env.Environment
import stone.lexer.Lexer
import stone.parser.BasicParser
import stone.token.Token
import stone.ui.CodeDialog

/**
 * Created by Junya on 2016/03/20.
 */
open class BasicInterpreter {
    companion object {
        fun run(bp: BasicParser, env: Environment) {
            val lexer = Lexer(CodeDialog())

            while (lexer.peek(0) != Token.EOF) {
                val tree = bp.parse(lexer)
                if (tree !is NullStmnt) println("=> " + tree.eval(env))
            }
        }
    }
}