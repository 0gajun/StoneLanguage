package stone.ast

import stone.env.Environment

/**
 * Created by Junya on 2016/04/02.
 */
class ArrayLiteral(c: List<ASTree>): ASTList(c){
    fun size() = numChildren()
    override fun eval(env: Environment): Any = this.map { it.eval(env) } .toMutableList()
}