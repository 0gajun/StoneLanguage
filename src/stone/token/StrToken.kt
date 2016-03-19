package stone.token

/**
 * Created by Junya on 2016/03/19.
 */
class StrToken(lineNumber: Int, str: String) : Token(lineNumber) {
    private val mLiteral = str

    override fun isString(): Boolean = true

    override fun getText(): String = mLiteral
}
