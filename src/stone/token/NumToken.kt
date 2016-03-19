package stone.token

/**
 * Created by Junya on 2016/03/19.
 */
class NumToken(lineNumber: Int, value: Int) : Token(lineNumber) {
    private val mValue = value

    override fun isNumber(): Boolean = true

    override fun getText(): String = mValue.toString()

    override fun getNumber(): Int = mValue
}