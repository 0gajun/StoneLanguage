package stone.firstclass

import stone.ast.ASTree
import stone.exception.StoneException
import java.lang.reflect.Method

/**
 * ネイティブ関数オブジェクトクラス
 * first-class object
 *
 * Created by Junya on 2016/03/27.
 */
class NativeFunction(name: String, m: Method) {
    protected val method: Method = m
    protected val name: String = name
    protected val numParams: Int = m.parameterTypes.size

    override fun toString(): String = "<native: " + hashCode() + ">"

    fun numOfParams() = numParams

    fun invoke(args: List<Any>, tree: ASTree): Any{
        try {
            return method.invoke(null, *args.toTypedArray())
        } catch (e: Exception) {
            throw StoneException("bad native function call: " + name, tree)
        }
    }
}
