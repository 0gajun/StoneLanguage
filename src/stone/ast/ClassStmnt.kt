package stone.ast

import stone.env.Environment
import stone.firstclass.ClassInfo

/**
 * class で定義されたクラスを表す抽象構文木
 *
 * Created by Junya on 2016/03/30.
 */
class ClassStmnt(c: List<ASTree>) : ASTList(c) {
    fun name(): String = (child(0) as ASTLeaf).token.getText()
    fun superClass(): String? = if (numChildren() < 3) null else (child(1) as ASTLeaf).token.getText()
    fun body(): ClassBody = child(numChildren() - 1) as ClassBody

    override fun eval(env: Environment): Any {
        val classInfo = ClassInfo(this, env)
        env.put(name(), classInfo)
        return name()
    }
}
