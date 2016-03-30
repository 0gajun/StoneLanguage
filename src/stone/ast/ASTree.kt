package stone.ast

import stone.env.Environment

/**
 * Created by Junya on 2016/03/19.
 */
interface ASTree : Iterable<ASTree> {
    fun child(i: Int) : ASTree
    fun numChildren() :Int
    fun children() : Iterator<ASTree>
    fun location() : String?
    fun eval(env: Environment): Any?
    override fun toString(): String

    override fun iterator(): Iterator<ASTree> = children()
}

