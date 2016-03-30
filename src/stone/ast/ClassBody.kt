package stone.ast

import stone.env.Environment

/**
 * クラスのbodyを表す抽象構文木
 *
 * Created by Junya on 2016/03/30.
 */
class ClassBody(c: List<ASTree>) : ASTList(c) {
    override fun eval(env: Environment): Any {
        this.forEach { it.eval(env) }
        return NullStmnt(listOf())
    }
}