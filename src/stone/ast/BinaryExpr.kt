package stone.ast

/**
 * 二項演算子を表す節
 *
 * Created by Junya on 2016/03/19.
 */
class BinaryExpr(children: List<ASTree>) : ASTList(children) {
    fun left(): ASTree = child(0)
    fun operator(): String = (child(1) as ASTLeaf).token.getText()
    fun right(): ASTree = child(2)
}
