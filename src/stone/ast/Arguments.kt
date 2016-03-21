package stone.ast

/**
 * 引数を表す抽象構文木の節
 *
 * Created by Junya on 2016/03/21.
 */
class Arguments(t: List<ASTree>) : ASTList(t) {
    fun size() = numChildren()
}

