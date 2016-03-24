package stone.chapters.chap7

import stone.ast.BlockStmnt
import stone.ast.ParameterList
import stone.env.Environment
import stone.env.NestedEnv

/**
 * 関数オブジェクトクラス
 *
 * Created by Junya on 2016/03/24.
 */
class Function(parameterList: ParameterList, body: BlockStmnt, env: Environment) {
    protected val parameters: ParameterList = parameterList
    protected val body: BlockStmnt = body
    protected val env: Environment = env

    fun parameters() = parameters
    fun body() = body
    fun makeEnv() = NestedEnv(env)

    override fun toString(): String = "<fun:" + hashCode() + ">"
}
