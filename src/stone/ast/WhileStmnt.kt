package stone.ast

import stone.StoneConst
import stone.env.Environment

/**
 * while block
 *
 * Created by Junya on 2016/03/19.
 */
class WhileStmnt(c: List<ASTree>) : ASTList(c) {
    fun condition(): ASTree = child(0)
    fun body(): ASTree = child(1)

    override fun toString(): String = "(while " + condition() + " " + body() + ")"

    override fun eval(env: Environment): Any? {
        var result : Any = 0
        while(condition().eval(env) == StoneConst.TRUE) {
            result = body().eval(env)
        }

        return result
    }
}