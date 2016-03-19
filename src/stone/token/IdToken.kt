package stone.token

/**
 * Created by Junya on 2016/03/19.
 */
class IdToken(lineNumber: Int, id: String) : Token(lineNumber) {
    private val mId = id

    override fun isIdentifier(): Boolean = true

    override fun getText(): String = mId
}
