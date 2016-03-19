package stone.chapters.chap3

import stone.lexer.Lexer
import stone.token.Token
import stone.ui.CodeDialog

/**
 * Created by Junya on 2016/03/18.
 */
object LexerRunner {
    fun run() {
        val l = Lexer(CodeDialog())
        var t: Token = l.read()

        while (t != Token.EOF) {
            println("=> " + t.getText() + ": " + t.getLineNumber())
            t = l.read()
        }
    }
}