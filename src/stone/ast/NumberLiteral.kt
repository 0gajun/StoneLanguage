package stone.ast

import stone.token.Token

/**
 * 整数リテラルの葉
 *
 * Created by Junya on 2016/03/19.
 */
class NumberLiteral(token: Token) : ASTLeaf(token) {
    fun value(): Int = token.getNumber()
}
