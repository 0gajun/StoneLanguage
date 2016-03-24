package stone.env

import java.util.*

/**
 * 局所変数と大域変数のスコープを管理可能にしたenv
 *
 * Created by Junya on 2016/03/21.
 */
class NestedEnv(e: Environment?) : Environment {
    private val values: HashMap<String, Any> = hashMapOf()
    private var outer: Environment? = e

    constructor() : this(null)

    override fun put(name: String, value: Any) {
        var e = where(name)
        if (e == null) {
            e = this
        }
        (e as NestedEnv).putNew(name, value)
    }

    override fun putNew(name: String, value: Any) {
        values.put(name, value)
    }

    override fun get(name: String): Any? = if (values.containsKey(name)) values[name] else outer?.get(name)

    override fun where(name: String): Environment? = if (values.containsKey(name)) this else outer?.where(name)

    override fun setOuter(e: Environment) {
        outer = e
    }
}
