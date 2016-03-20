package stone.ast

import stone.env.Environment

/**
 * block
 *
 * Created by Junya on 2016/03/19.
 */
class BlockStmnt(c: List<ASTree>) : ASTList(c) {
    override fun eval(env: Environment): Any {
        return children.filter { it !is NullStmnt }.fold(0 as Any, {
            c, t -> t.eval(env)
        })
    }
}
