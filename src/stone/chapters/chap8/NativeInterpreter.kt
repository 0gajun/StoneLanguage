package stone.chapters.chap8

import stone.chapters.chap6.BasicInterpreter
import stone.env.Natives
import stone.env.NestedEnv
import stone.parser.ClosureParser

/**
 * Created by Junya on 2016/03/27.
 */
class NativeInterpreter: BasicInterpreter() {
    companion object {
        fun run() {
            BasicInterpreter.run(ClosureParser(), Natives().environment(NestedEnv()))
        }
    }
}
