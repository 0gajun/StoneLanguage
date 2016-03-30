package stone.ast

import stone.StoneConst
import stone.env.Environment

/**
 * if block
 *
 * Created by Junya on 2016/03/19.
 */
class IfStmnt(c: List<ASTree>) : ASTList(c){
    fun condition() : ASTree = child(0)
    fun thenBlock() : ASTree = child(1)
    fun elseBlock() : ASTree? = if (numChildren() > 2) child(2) else null

    override fun toString(): String
            = "(if " + condition() + " " + thenBlock() + " else " + elseBlock() + ")"

    override fun eval(env: Environment): Any? {
        val c = condition().eval(env)

        return if (c == StoneConst.TRUE) {
            thenBlock().eval(env)
        } else {
            val block = elseBlock()
            if (block != null) block.eval(env) else 0
        }
    }
}