package stone.env

/**
 * Created by Junya on 2016/04/04.
 */
class ArrayEnv(val size: Int, val outer: Environment) : Environment {
    val values = Array(size, { i -> Any() })

    override fun put(name: String, value: Any) {
        throw UnsupportedOperationException()
    }

    override fun putNew(name: String, value: Any) {
        throw UnsupportedOperationException()
    }

    override fun get(name: String): Any? {
        throw UnsupportedOperationException()
    }

    override fun where(name: String): Environment? {
        throw UnsupportedOperationException()
    }

    override fun setOuter(e: Environment) {
        throw UnsupportedOperationException()
    }

}
