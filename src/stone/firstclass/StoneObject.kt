package stone.firstclass

import stone.env.Environment

/**
 * Stone言語におけるオブジェクトを表現するファーストクラス
 * first-class object
 *
 * Created by Junya on 2016/03/30.
 */
class StoneObject(val env: Environment) {
    override fun toString(): String = "<object: " + hashCode() + ">"


    @Throws(AccessException::class)
    fun read(member: String): Any {
        return getEnv(member).get(member) ?: throw AccessException()
    }

    fun write(member: String, value: Any) {
        getEnv(member).put(member, value)
    }

    @Throws(AccessException::class)
    private fun getEnv(member: String): Environment {
        val e = env.where(member)
        if (e != null && e == env) {
            return e
        } else {
            throw AccessException()
        }
    }

    class AccessException : Exception()
}
