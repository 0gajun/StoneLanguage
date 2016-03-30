package stone.ast

import stone.env.Environment
import stone.exception.StoneException

/**
 * root class of Node
 *
 * Created by Junya on 2016/03/19.
 */
open class ASTList(children: List<ASTree>) : ASTree {

    protected val children = children

    override fun child(i: Int): ASTree = children[i]

    override fun numChildren(): Int = children.size

    override fun children(): Iterator<ASTree> = children.iterator()

    override fun eval(env: Environment): Any? {
        throw StoneException("cannot eval: " + toString(), this)
    }

    override fun location(): String?
            = children.find { !it.location().isNullOrEmpty() }?.location()

    override fun toString(): String
            = children.joinToString(separator = " ", prefix = "(", postfix = ")",transform = { it.toString() })
}
