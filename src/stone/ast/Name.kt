package stone.ast

import stone.token.Token

/**
 * 変数名を表す葉
 *
 * Created by Junya on 2016/03/19.
 */
class Name(t: Token) : ASTLeaf(t) {
    fun name(): String = token.getText()
}
