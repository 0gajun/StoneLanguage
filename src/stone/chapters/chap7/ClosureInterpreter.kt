package stone.chapters.chap7

import stone.chapters.chap6.BasicInterpreter
import stone.env.NestedEnv
import stone.parser.ClosureParser

/**
 * クロージャのインタプリタ
 *
 * Created by Junya on 2016/03/26.
 */
class ClosureInterpreter:BasicInterpreter() {
    companion object {
        fun run() {
            BasicInterpreter.run(ClosureParser(), NestedEnv())
        }
    }
}
