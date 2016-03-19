package stone.ast

import stone.token.Token

/**
 * - primary を表す
 *
 * Created by Junya on 2016/03/19.
 */
class NegativeExpr(c: List<ASTree>) : ASTList(c) {
    fun operand() = children[0]
    override fun toString(): String = "-" + operand()
}
