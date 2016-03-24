package stone.ast

import stone.env.Environment
import stone.chapters.chap7.Function
import stone.exception.StoneException

/**
 * 引数を表す抽象構文木の節
 *
 * Created by Junya on 2016/03/21.
 */
class Arguments(t: List<ASTree>) : ASTList(t) {
    fun size() = numChildren()

    fun eval(callerEnv: Environment, value: Any): Any {
        if (value !is Function) {
            throw StoneException("bad function", this)
        }
        val func: Function = value
        val parameters = func.parameters()

        if (size() != parameters.size()) {
            throw StoneException("bad number of arguments", this)
        }

        val newEnv = func.makeEnv()
        this.forEachIndexed { i, e ->
            parameters.eval(newEnv, i, e.eval(callerEnv))
        }
        return func.body().eval(newEnv)
    }
}

