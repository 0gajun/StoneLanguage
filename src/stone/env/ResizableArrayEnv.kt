package stone.env

/**
 * 大域変数用のEnvironment
 *
 * Created by Junya on 2016/04/16.
 */
class ResizableArrayEnv : ArrayEnv(10, null) {
    protected val names: Symbols = Symbols()

    override fun symbols(): Symbols = names

    override fun get(name: String): Any? {
        val i = names.find(name)
        return if (i == null) {
            outer?.get(name)
        } else {
            values[i]
        }
    }

    override fun put(name: String, value: Any) {
        val env = where(name) ?: this
        env.put(name, value)
    }

    override fun putNew(name: String, value: Any) {
        assign(names.putNew(name), value)
    }

    override fun where(name: String): Environment? {
        return if (names.find(name) != null) {
            this
        } else if (outer != null) {
            (outer as Environment2).where(name)
        } else {
            null
        }
    }

    protected fun assign(index: Int, value: Any) {
        if (index > values.size) {
            val orgLen = values.size
            var newLen = orgLen * 2
            if (index >= newLen) {
                newLen = index + 1
            }
            values = Array(newLen, { i -> if (i < orgLen) values[i] else Any() })
        }
        values[index] = value
    }
}
