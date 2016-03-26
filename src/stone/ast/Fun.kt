package stone.ast

import stone.chapters.chap7.Function
import stone.env.Environment

/**
 * クロージャを定義するAST
 *
 * Created by Junya on 2016/03/26.
 */
class Fun(c: List<ASTree>): ASTList(c){
    fun parameters() = child(0) as ParameterList
    fun body() = child(1) as BlockStmnt
    override fun toString(): String = "(fun " + parameters() + " " + body() + ")"

    override fun eval(env: Environment): Any = Function(parameters(), body(), env)
}