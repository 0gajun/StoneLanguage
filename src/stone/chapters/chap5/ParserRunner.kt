package stone.chapters.chap5

import stone.lexer.Lexer
import stone.parser.BasicParser
import stone.token.Token
import stone.ui.CodeDialog

/**
 * Created by Junya on 2016/03/19.
 */
object ParserRunner {
    fun run() {
        val l = Lexer(CodeDialog())
        val bp = BasicParser()
        while (l.peek(0) != Token.EOF) {
            val tree= bp.parse(l)
            println("=> " + tree.toString())
        }
    }
}