package stone

import stone.chapters.chap3.LexerRunner
import stone.chapters.chap5.ParserRunner
import stone.chapters.chap6.BasicInterpreter
import stone.chapters.chap7.ClosureInterpreter
import stone.chapters.chap7.FunctionInterpreter
import stone.chapters.chap8.NativeInterpreter
import stone.chapters.chap9.ClassInterpreter
import stone.env.BasicEnv
import stone.parser.BasicParser

/**
 * Created by Junya on 2016/03/18.
 */

fun main(args: Array<String>) {
    ClassInterpreter.run()
}
