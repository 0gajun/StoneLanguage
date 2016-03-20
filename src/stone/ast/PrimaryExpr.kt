package stone.ast

/**
 * Created by Junya on 2016/03/19.
 */
class PrimaryExpr(c: List<ASTree>) : ASTList(c) {
    companion object {
        @JvmStatic
        fun create(c: List<ASTree>) : ASTree = if (c.size == 1) c.single() else PrimaryExpr(c)
    }
}