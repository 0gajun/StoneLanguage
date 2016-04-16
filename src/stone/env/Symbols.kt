package stone.env;

/**
 * ResizableArrayEnvにおいて，大域変数のSymbol情報を管理するクラス
 *
 * Created by Junya on 2016/04/16.
 */
class Symbols(val outer: Symbols?) {
    companion object {
        data class Location(val nest: Int, val index: Int)
    }

    protected val table: MutableMap<String, Int> = mutableMapOf()

    constructor() : this(null)

    fun size(): Int = table.size
    fun append(s: Symbols) {
        table.putAll(s.table)
    }

    fun find(key: String): Int? = table[key]
    fun get(key: String): Location? = get(key, 0)
    fun get(key: String, nest: Int): Location? {
        val index = table[key]
        if (index == null) {
            return outer?.get(key, nest + 1)
        } else {
            return Location(nest, index)
        }
    }

    fun putNew(key: String): Int = find(key) ?: add(key)

    fun put(key: String): Location? {
        val loc = get(key, 0)
        return loc ?: Location(0, add(key))
    }

    fun add(key: String): Int = table.size.apply { table.put(key, this) }

}
