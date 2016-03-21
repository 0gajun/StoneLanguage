package stone.env

import java.util.*

/**
 * 環境を表すEnvironmentの実装
 *
 * Created by Junya on 2016/03/20.
 */
class BasicEnv : Environment {
    val values : HashMap<String, Any> = hashMapOf()

    override fun put(name: String, value: Any) {
        values.put(name, value)
    }

    override fun get(name: String): Any? = values.get(name)

    override fun where(name: String): Environment? {
        throw UnsupportedOperationException()
    }
}