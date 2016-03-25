package stone.chapters.chap7

import stone.chapters.chap6.BasicInterpreter
import stone.env.Environment
import stone.env.NestedEnv
import stone.parser.BasicParser
import stone.parser.FuncParser

/**
 * Created by Junya on 2016/03/25.
 */
class FunctionInterpreter : BasicInterpreter() {
    companion object {
        fun run() {
            BasicInterpreter.run(FuncParser(), NestedEnv())
        }
    }
}
