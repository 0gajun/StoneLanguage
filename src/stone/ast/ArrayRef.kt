package stone.ast

import stone.env.Environment
import stone.exception.StoneException

/**
 * Created by Junya on 2016/04/02.
 */
class ArrayRef(c: List<ASTree>): Postfix(c) {
    fun index() = child(0)
    override fun toString(): String = "[" + index() + "]"

    override fun eval(env: Environment, value: Any): Any {
        if (value is MutableList<*>) {
            val index = index().eval(env)
            if (index is Int) {
                if (index > value.size) {
                    throw ArrayIndexOutOfBoundsException(index)
                }
                return value[index]!!
            }
        }

        throw StoneException("bad array access", this)
    }
}