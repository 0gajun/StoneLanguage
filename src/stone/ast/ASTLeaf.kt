package stone.ast

import stone.env.Environment
import stone.exception.StoneException
import stone.token.Token

/**
 * root class of leaf
 *
 * Created by Junya on 2016/03/19.
 */
open class ASTLeaf(token: Token) : ASTree {
    companion object {
        private val emptyChildren = listOf<ASTree>()
    }

    val token = token

    override fun child(i: Int): ASTree = throw IndexOutOfBoundsException()

    override fun numChildren(): Int = 0

    override fun eval(env: Environment): Any? {
        throw StoneException("cannot eval: " + toString(), this)
    }

    override fun children(): Iterator<ASTree> = emptyChildren.iterator()

    override fun location(): String = "at line " + token.getLineNumber()

    override fun toString() : String = token.getText()
}
