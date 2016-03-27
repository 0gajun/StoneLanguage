package stone.ast

import stone.env.Environment
import stone.firstclass.Function
import stone.exception.StoneException
import stone.firstclass.NativeFunction

/**
 * 引数を表す抽象構文木の節
 *
 * Created by Junya on 2016/03/21.
 */
class Arguments(t: List<ASTree>) : Postfix(t) {
    fun size() = numChildren()

    override fun eval(env: Environment, value: Any): Any {
        return when (value) {
            is Function -> evalNonNativeFunc(env, value)
            is NativeFunction -> evalNativeFunc(env, value)
            else -> throw StoneException("bad function, this")
        }
    }

    private fun evalNonNativeFunc(env: Environment, func: Function): Any {
        val parameters = func.parameters()

        if (size() != parameters.size()) {
            throw StoneException("bad number of arguments", this)
        }

        val newEnv = func.makeEnv()
        this.forEachIndexed { i, e ->
            parameters.eval(newEnv, i, e.eval(env))
        }
        return func.body().eval(newEnv)
    }

    private fun evalNativeFunc(env: Environment, func: NativeFunction): Any {
        if (size() != func.numOfParams()) {
            throw StoneException("bad number of arguments", this)
        }

        val args = this.mapIndexed { j, c -> c.eval(env) }

        return func.invoke(args, this)
    }
}

