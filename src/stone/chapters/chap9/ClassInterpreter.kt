package stone.chapters.chap9

import stone.chapters.chap6.BasicInterpreter
import stone.env.Natives
import stone.env.NestedEnv
import stone.parser.ClassParser

/**
 * Created by Junya on 2016/04/02.
 */
class ClassInterpreter: BasicInterpreter() {
    companion object {
        fun run() {
            BasicInterpreter.run(ClassParser(), Natives().environment(NestedEnv()))
        }
    }
}