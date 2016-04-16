package stone.env;

/**
 * chap11 でReviserにより拡張されたEnvEx2を表すインタフェース
 *
 * Created by Junya on 2016/04/16.
 */
interface Environment2 : Environment {
    fun symbols(): Symbols
    fun put(nest: Int, index: Int, value: Any)
    fun get(nest: Int, index: Int): Any?

    override fun setOuter(e: Environment) {
        throw UnsupportedOperationException()
    }
}
