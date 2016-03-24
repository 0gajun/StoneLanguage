package stone.ast

import stone.env.Environment

/**
 * Created by Junya on 2016/03/19.
 */
class PrimaryExpr(c: List<ASTree>) : ASTList(c) {
    fun operand() = child(0)
    fun postfix(nest: Int) : Postfix = child(numChildren() - nest - 1) as Postfix
    fun hasPostfix(nest: Int) : Boolean = numChildren() - nest > 1
    override fun eval(env: Environment): Any = evalSubExpr(env, 0)
    fun evalSubExpr(env: Environment, nest: Int) :Any {
        if (hasPostfix(nest)) {
            val target = evalSubExpr(env, nest + 1)
            return postfix(nest).eval(env, target)
        } else {
            return operand().eval(env)
        }
    }
    companion object {
        @JvmStatic
        fun create(c: List<ASTree>) : ASTree = if (c.size == 1) c.single() else PrimaryExpr(c)
    }
}