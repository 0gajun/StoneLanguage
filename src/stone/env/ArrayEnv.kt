package stone.env

import stone.exception.StoneException

/**
 * ハッシュではなく，Arrayを使うことで変数へのアクセスを高速化したEnv
 *
 * Created by Junya on 2016/04/04.
 */
open class ArrayEnv(val size: Int, var outer: Environment2?) : Environment2 {
    var values = Array(size, { i -> Any() })

    override fun symbols(): Symbols {
        throw StoneException("no symbols")
    }

    override fun put(nest: Int, index: Int, value: Any) {
        if (nest == 0) {
            values[index] = value
        } else if (outer == null) {
            throw StoneException("no outer environment")
        } else {
            (outer as Environment2).put(nest - 1, index, value)
        }
    }

    override fun get(nest: Int, index: Int): Any? = if (nest == 0) values else outer?.get(nest - 1, index)

    override fun put(name: String, value: Any) {
        error(name)
    }

    override fun putNew(name: String, value: Any) {
        error(name)
    }

    override fun get(name: String): Any? {
        error(name)
        return null
    }

    override fun where(name: String): Environment? {
        error(name)
        return null
    }

    private fun error(name: String) {
        throw StoneException("cannot access by name: " + name)
    }

}
