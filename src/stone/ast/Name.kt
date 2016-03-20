package stone.ast

import stone.env.Environment
import stone.exception.StoneException
import stone.token.Token

/**
 * 変数名を表す葉
 *
 * Created by Junya on 2016/03/19.
 */
class Name(t: Token) : ASTLeaf(t) {
    fun name(): String = token.getText()

    override fun eval(env: Environment): Any {
        val v = env.get(name())
        if (v != null) {
            return v
        } else {
            throw StoneException("undefined name: " + name(), this)
        }
    }
}
