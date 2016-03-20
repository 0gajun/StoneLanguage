package stone.exception

import stone.ast.ASTree

/**
 * Created by Junya on 2016/03/19.
 */
class StoneException(msg: String) : RuntimeException(msg) {
    constructor(m: String, t: ASTree) : this(m + " " + t.location())
}
