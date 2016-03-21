package stone.ast

/**
 * 関数パラメタを表す抽象構文木の節
 *
 * Created by Junya on 2016/03/21.
 */

class ParameterList(t: List<ASTree>) : ASTList(t) {
    fun name(i: Int): String = (child(i) as ASTLeaf).token.getText()
    fun size(): Int = numChildren()
}
