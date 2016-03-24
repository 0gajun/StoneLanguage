package stone.ast

import stone.env.Environment

/**
 *
 *
 * Created by Junya on 2016/03/24.
 */
abstract class Postfix(c: List<ASTree>) : ASTList(c) {
    abstract fun eval(env: Environment, value: Any) : Any
}