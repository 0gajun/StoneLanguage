package stone.parser

import stone.ast.ASTree
import stone.exception.ParseException
import stone.lexer.Lexer
import kotlin.reflect.KClass

/**
 * Created by Junya on 2016/03/27.
 */
class Parser {
    companion object {
        abstract class Element {
            @Throws(ParseException::class)
            abstract fun parse(lexer: Lexer, res: MutableList<ASTree>)

            @Throws(ParseException::class)
            abstract fun match(lexer: Lexer): Boolean
        }

        class Tree(p: Parser) : Element() {
            protected val parser: Parser = p
            override fun parse(lexer: Lexer, res: MutableList<ASTree>) {
                res.add(parser.parse(lexer))
            }

            override fun match(lexer: Lexer): Boolean = parser.match(lexer)
        }

        val factoryName = "create"
        abstract class Factory {
            @Throws(Exception::class)
            abstract fun make0(arg: Any): ASTree
            fun make(arg: Any): ASTree {
                try {
                    return make0(arg)
                } catch(e1: IllegalArgumentException) {
                    throw e1
                } catch(e2: Exception) {
                    throw RuntimeException(e2) // this compiler is broken
                }
            }

            companion object {
                fun getForASTList(clazz: KClass<out ASTree>?): Factory {
                    val f = get(clazz, List::class)
                    if (f == null) {
                        f =
                    }

                    return f!!
                }

                fun get(clazz: KClass<out ASTree>?, argType: KClass<*>): Factory? {
                    if (clazz == null) {
                        return null
                    }
                    try {
                        val m = clazz.java.getMethod(factoryName, *listOf(argType.java).toTypedArray())
                        return
                    } catch(e: NoSuchMethodException) {}
                    try {
                        val c = clazz.java.getConstructor(argType.java)
                        return
                    } catch(e: NoSuchMethodException) {
                        throw RuntimeException(e)
                    }
                }
            }
        }

        fun rule(): Parser = rule(null)
        fun rule(clazz: KClass<out ASTree>?): Parser = Parser(clazz)
    }

    var elements: MutableList<Element>
    var factory: Factory

    constructor(p: Parser) {
        elements = p.elements
        factory = p.factory
    }

    constructor(clazz: KClass<out ASTree>?) {
        elements = mutableListOf()
        factory = Factory.getForASTList(clazz)
    }

    @Throws(ParseException::class)
    fun parse(lexer: Lexer): ASTree {
        val results: MutableList<ASTree> = mutableListOf()
        elements.forEach { it.parse(lexer, results) }

        return factory.make(results)
    }

    fun match(lexer: Lexer): Boolean {

    }

    fun reset(): Parser {
        elements = mutableListOf()
        return this
    }

    fun reset(clazz: KClass<out ASTree>): Parser {
        elements = mutableListOf()
        factory = Factory.getForASTList(clazz)
        return this
    }
}
