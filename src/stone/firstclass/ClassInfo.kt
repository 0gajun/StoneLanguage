package stone.firstclass

import stone.ast.ClassStmnt
import stone.env.Environment
import stone.exception.StoneException

/**
 * クラスを表すファーストクラスオブジェクト
 * first-class object
 *
 * Created by Junya on 2016/03/30.
 */
class ClassInfo(private val definition: ClassStmnt, private val environment: Environment) {
    protected val superClass: ClassInfo?

    init {
        val obj = if (definition.superClass().isNullOrBlank()) {
            null
        } else {
            environment.get(definition.superClass()!!)
        }
        superClass = if (obj == null) null else if (obj is ClassInfo) obj else {
            throw StoneException("unknown super class: " + definition.superClass(), definition)
        }
    }

    fun name() = definition.name()
    fun superClass() = superClass
    fun body() = definition.body()
    fun environment() = environment
    override fun toString(): String = "<class " + name() + ">"
}
