package stone.ast

import stone.env.Environment
import stone.token.Token

/**
 * Created by Junya on 2016/03/19.
 */
class StringLiteral(t: Token) : ASTLeaf(t) {
    fun value() = token.getText()

    override fun eval(env: Environment): Any = value()
}
