package stone.exception

import stone.token.Token
import java.io.IOException

/**
 * Created by Junya on 2016/03/18.
 */
class ParseException : Exception {

    constructor(errMsg: String) : super(errMsg)
    constructor(token: Token) : this(exceptionMessage(token, ""))
    constructor(token: Token, msg: String) : this(exceptionMessage(token, msg))
    constructor(e: IOException) : super(e)

    private companion object {
        fun exceptionMessage(token: Token, msg: String): String {
            return "syntax error around " + location(token) + ". " + msg
        }

        fun location(token: Token): String {
            return if (token == Token.EOF) {
                "the last line"
            } else {
                "\"" + token.getText() + "\" at line " + token.getLineNumber()
            }
        }
    }


}
