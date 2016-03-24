package stone.ast

import stone.env.Environment

/**
 * 関数パラメタ
 *
 * Created by Junya on 2016/03/21.
 */

class ParameterList(t: List<ASTree>) : ASTList(t) {
    fun name(i: Int) : String = (child(i) as ASTLeaf).token.getText()
    fun size() = numChildren()

    fun eval(env:Environment, index: Int, value: Any) {
        env.putNew(name(index), value)
    }
}
