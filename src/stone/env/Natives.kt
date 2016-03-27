package stone.env

import stone.exception.StoneException
import stone.firstclass.NativeFunction
import javax.swing.JOptionPane
/**
 * Created by Junya on 2016/03/27.
 */
class Natives() {
    fun environment(env: Environment): Environment {
        appendNatives(env)
        return env;
    }

    private fun appendNatives(env: Environment) {
        append(env, "print", Natives::class.java, "print", Any::class.java)
        append(env, "read", Natives::class.java, "read")
        append(env, "length", Natives::class.java, "length", String::class.java)
        append(env, "toInt", Natives::class.java, "toInt", Any::class.java)
        append(env, "currentTime", Natives::class.java, "currentTime")
    }

    private fun append(env: Environment, name: String, clazz: Class<out Any?>, methodName: String, vararg params: Class<out Any?>) {
        val m = try {
            clazz.getMethod(methodName, *params)
        } catch (e: Exception) {
            throw StoneException("cannot find a native function: " + methodName)
        }
        env.put(name, NativeFunction(methodName, m))
    }

    companion object {
        @JvmStatic
        fun print(obj: Any): Int {
            System.out.println(obj.toString())
            return 0
        }

        @JvmStatic
        fun read(): String {
            return JOptionPane.showInputDialog(null)
        }

        @JvmStatic
        fun length(s: String): Int = s.length

        @JvmStatic
        fun toInt(value: Any): Int {
            return when(value) {
                is String -> value.toInt()
                is Int -> value
                else -> throw NumberFormatException(value.toString())
            }
        }

        @JvmStatic
        val startTime = System.currentTimeMillis()
        @JvmStatic
        fun currentTime(): Int = (System.currentTimeMillis() - startTime).toInt()
    }

}
