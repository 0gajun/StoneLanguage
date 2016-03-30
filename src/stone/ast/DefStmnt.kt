package stone.ast

import stone.firstclass.Function
import stone.env.Environment

/**
 * 関数定義の抽象構文木の節
 *
 * Created by Junya on 2016/03/21.
 */
class DefStmnt(t: List<ASTree>) : ASTList(t) {
    fun name(): String = (child(0) as ASTLeaf).token.getText()
    fun parameters(): ParameterList = child(1) as ParameterList
    fun body(): BlockStmnt = child(2) as BlockStmnt
    override fun toString(): String = "(def " + name() + " " + parameters() + " " + body() + ")"
    override fun eval(env: Environment): Any {
        env.putNew(name(), Function(parameters(), body(), env))
        return name()
    }
}
