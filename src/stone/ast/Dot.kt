package stone.ast

import stone.env.Environment
import stone.env.NestedEnv
import stone.exception.StoneException
import stone.firstclass.ClassInfo
import stone.firstclass.StoneObject

/**
 * クラスオブジェクトのメンバへのアクセスを表す抽象構文木
 *
 * Created by Junya on 2016/03/30.
 */
class Dot(c: List<ASTList>) : Postfix(c) {
    fun name(): String = (child(0) as ASTLeaf).token.getText()
    override fun toString(): String = "." + name()

    override fun eval(env: Environment, value: Any): Any {
        val member = name()
        if (value is ClassInfo && "new".equals(member)) {
            return createObject(value)
        } else if (value is StoneObject) {
            try {
                return value.read(member)
            } catch (ignored: StoneObject.AccessException) {
            }
        }

        throw StoneException("bad member access: " + member, this)
    }

    private fun createObject(ci: ClassInfo): Any {
        val e = NestedEnv(ci.environment())
        val so = StoneObject(e)
        e.putNew("this", so)
        initObject(ci, e)
        return so
    }

    private fun initObject(ci: ClassInfo, env: Environment) {
        val superClass = ci.superClass()
        if (superClass != null) {
            initObject(superClass, env)
        }
        ci.body().eval(env)
    }
}
