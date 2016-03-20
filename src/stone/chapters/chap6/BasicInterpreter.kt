package stone.chapters.chap6

import stone.ast.NullStmnt
import stone.env.BasicEnv
import stone.lexer.Lexer
import stone.parser.BasicParser
import stone.token.Token
import stone.ui.CodeDialog

/**
 * Created by Junya on 2016/03/20.
 */
object BasicInterpreter {
    fun run() {
        val bp = BasicParser()
        val env = BasicEnv()
        val lexer = Lexer(CodeDialog())

        while (lexer.peek(0) != Token.EOF) {
            val tree = bp.parse(lexer)
            if (tree !is NullStmnt) print("=> " + tree.eval(env))
        }
    }
}