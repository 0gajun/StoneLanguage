package stone.env

/**
 * 実行環境を表すインタフェース
 *
 * Created by Junya on 2016/03/20.
 */
interface Environment {
    fun put(name: String, value: Any): Unit
    fun putNew(name: String, value: Any): Unit
    fun get(name: String): Any?
    fun where(name: String): Environment?
    fun setOuter(e: Environment): Unit
}