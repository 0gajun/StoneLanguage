package stone.token

import stone.exception.StoneException

/**
 * Created by Junya on 2016/03/17.
 */
abstract class Token(lineNumber: Int) {

    companion object {
        val EOF: Token = object : Token(-1) {

        }
        val EOL = "\\n"
    }


    private val mLineNumber: Int = lineNumber

    fun getLineNumber(): Int {
        return mLineNumber
    }

    open fun isIdentifier(): Boolean {
        return false
    }

    open fun isNumber(): Boolean {
        return false
    }

    open fun isString(): Boolean {
        return false
    }

    open fun getNumber(): Int {
        throw StoneException("not number token")
    }

    open fun getText(): String {
        return ""
    }

}