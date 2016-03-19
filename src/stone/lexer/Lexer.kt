package stone.lexer

import stone.exception.ParseException
import stone.token.IdToken
import stone.token.NumToken
import stone.token.StrToken
import stone.token.Token
import java.io.IOException
import java.io.LineNumberReader
import java.io.Reader
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Junya on 2016/03/17.
 */
class Lexer(r: Reader) {
    private val REGEX_PATTERN = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")" + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?"
    private val mRegex = Regex(REGEX_PATTERN)
    private val mPattern = Pattern.compile(REGEX_PATTERN)

    private var mHasMore: Boolean = true
    private var mReader: LineNumberReader = LineNumberReader(r)
    private var mQueue: MutableList<Token> = arrayListOf()

    fun read() : Token {
        if (!fillQueue(0)) {
            return Token.EOF
        }
        return mQueue.removeAt(0)
    }

    fun peek(i: Int) : Token {
        if (!fillQueue(i)) {
            return Token.EOF
        }
        return mQueue[i]
    }

    /**
     * @param i iの数だけQueueをTokenで満たす
     */
    private fun fillQueue(i: Int) : Boolean {
        while (i >= mQueue.size) {
            if (!mHasMore) {
                return false
            }
            readLine()
        }
        return true
    }

    protected fun readLine() {
        var line: String? = null

        try {
            line = mReader.readLine()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (line == null) {
            mHasMore = false
            return
        }

        val lineNo = mReader.lineNumber
        val matcher = mPattern.matcher(line)
        matcher.useTransparentBounds(true).useAnchoringBounds(false)

        var pos = 0
        val endPos = line.length
        while (pos < endPos) {
            matcher.region(pos, endPos)
            if (matcher.lookingAt()) {
                addToken(lineNo, matcher)
                pos = matcher.end()
            } else {
                throw ParseException("bad token at line " + lineNo)
            }
        }
        mQueue.add(IdToken(lineNo, Token.EOL))
    }

    /**
     * KotlinのRegexを使ったver.
     * Java正規表現エンジンで使えるuseTransparentBoundsが使えない為, 一旦使うの保留
     *
     * Issue上げてみたさ
     */
    protected fun _readLine() {
        var line: String? = null

        try {
            line = mReader.readLine()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (line == null) {
            mHasMore = false
            return
        }

        val lineNo = mReader.lineNumber
        val matcher = mPattern.matcher(line)
        matcher.useTransparentBounds(true).useAnchoringBounds(false)

        var pos = 0
        val endPos = line.length

        while (pos < endPos) {
            val matchResult = mRegex.find(line, pos)
            if (matchResult != null) {
                _addToken(lineNo, matchResult)
                pos = matchResult.range.last + 1 // +1 because we cannot use useTransparentBounds
            } else {
                throw ParseException("bad token at line " + lineNo)
            }
        }

        mQueue.add(IdToken(lineNo, Token.EOL))
    }

    private fun addToken(lineNo: Int, matcher: Matcher) {
        val m = matcher.group(1)

        if (m.isNullOrBlank() || !matcher.group(2).isNullOrBlank()) {
            // if only spaces or only comments
            return
        }

        val token = if (!matcher.group(3).isNullOrBlank()) {
            NumToken(lineNo, m.toInt())
        } else if (!matcher.group(4).isNullOrBlank()) {
            StrToken(lineNo, toStringLiteral(m))
        } else {
            IdToken(lineNo, m)
        }
        mQueue.add(token)
    }
    private fun _addToken(lineNo: Int, match: MatchResult) {
        val values = match.groupValues
        val v = values[1]
        if (v.isNullOrBlank() || !values[2].isNullOrBlank()) {
            print("null")
            // if only spaces or only comments
            return
        }

        val token = if (!values[3].isNullOrBlank()) {
            NumToken(lineNo, v.toInt())
        } else if (!values[4].isNullOrBlank()) {
            StrToken(lineNo, toStringLiteral(v))
        } else {
            IdToken(lineNo, v)
        }
        mQueue.add(token)
    }

    private fun toStringLiteral(s: String): String {
        var res = ""
        val i = s.iterator()
        while (i.hasNext()) {
            var c = i.nextChar()

            if (c == '\\' && i.hasNext()) {
                val c2 = i.nextChar()
                c = when (c2) {
                    '"' -> '"'
                    '\\'-> '\\'
                    'n' -> '\n'
                    else -> c
                }
            }
            res += c
        }
        return res
    }


}