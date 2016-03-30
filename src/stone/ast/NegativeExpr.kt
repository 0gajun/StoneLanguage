package stone.ast

import stone.env.Environment
import stone.exception.StoneException

/**
 * - primary を表す
 *
 * Created by Junya on 2016/03/19.
 */
class NegativeExpr(c: List<ASTree>) : ASTList(c) {
    fun operand() = children[0]
    override fun toString(): String = "-" + operand()

    override fun eval(env: Environment): Any? {
        val v = operand().eval(env)
        if (v is Int) {
            return -1 * v
        } else {
            throw StoneException("bad type for -", this)
        }
    }
}
