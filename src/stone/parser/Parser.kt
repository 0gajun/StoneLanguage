package stone.parser

import stone.ast.ASTLeaf
import stone.ast.ASTList
import stone.ast.ASTree
import stone.exception.ParseException
import stone.lexer.Lexer
import stone.token.Token
import java.util.*
import kotlin.reflect.KClass

/**
 * サンプルのParserをとりあえずKotlin文法に直したもの
 *
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

        class Tree(val parser: Parser) : Element() {
            override fun parse(lexer: Lexer, res: MutableList<ASTree>) {
                res.add(parser.parse(lexer))
            }

            override fun match(lexer: Lexer): Boolean = parser.match(lexer)
        }

        class OrTree(p: List<Parser>) : Element() {
            private val parsers = p.toMutableList()
            override fun parse(lexer: Lexer, res: MutableList<ASTree>) {
                val p = choose(lexer)
                if (p == null) {
                    throw ParseException(lexer.peek(0))
                } else {
                    res.add(p.parse(lexer))
                }
            }

            override fun match(lexer: Lexer): Boolean = choose(lexer) != null

            fun choose(lexer: Lexer): Parser? = parsers.firstOrNull { it.match(lexer) }

            fun insert(p: Parser) {
                parsers.add(0, p)
            }
        }

        class Repeat(val parser: Parser, val onlyOnce: Boolean) : Element() {
            override fun parse(lexer: Lexer, res: MutableList<ASTree>) {
                while (parser.match(lexer)) {
                    val t = parser.parse(lexer)
                    if (t !is ASTList || t.numChildren() > 0) {
                        res.add(t)
                    }
                    if (onlyOnce) {
                        break
                    }
                }
            }

            override fun match(lexer: Lexer): Boolean = parser.match(lexer)
        }

        abstract class AToken(type: KClass<out ASTLeaf>?) : Element() {
            val factory: Factory

            init {
                val t = type ?: ASTLeaf::class
                factory = Factory.get(t, Token::class)!!
            }

            override fun parse(lexer: Lexer, res: MutableList<ASTree>) {
                val t = lexer.read()
                if (test(t)) {
                    val leaf = factory.make(t)
                    res.add(leaf)
                } else {
                    throw ParseException(t)
                }
            }

            override fun match(lexer: Lexer): Boolean = test(lexer.peek(0))

            abstract fun test(t: Token): Boolean
        }

        class IdToken(type: KClass<out ASTLeaf>?, val reserved: HashSet<String>) : AToken(type) {
            override fun test(t: Token): Boolean = t.isIdentifier() && !reserved.contains(t.getText())
        }

        class NumToken(type: KClass<out ASTLeaf>?) : AToken(type) {
            override fun test(t: Token): Boolean = t.isNumber()
        }

        class StrToken(type: KClass<out ASTLeaf>?) : AToken(type) {
            override fun test(t: Token): Boolean = t.isString()
        }

        open class Leaf(val tokens: List<String>) : Element() {
            override fun parse(lexer: Lexer, res: MutableList<ASTree>) {
                val t = lexer.read()
                if (t.isIdentifier() && tokens.contains(t.getText())) {
                    find(res, t)
                    return
                }

                throw if (tokens.isEmpty()) ParseException(t) else ParseException(tokens.first() + " expected.", t)
            }

            override fun match(lexer: Lexer): Boolean {
                val t = lexer.peek(0)
                return if (t.isIdentifier()) (tokens.firstOrNull { it.equals(t.getText()) } != null) else false
            }

            open fun find(res: MutableList<ASTree>, t: Token) {
                res.add(ASTLeaf(t))
            }
        }

        class Skip(t: List<String>) : Leaf(t) {
            override fun find(res: MutableList<ASTree>, t: Token) {
            }
        }

        data class Precedence(val value: Int, val leftAssoc: Boolean)

        class Operators : HashMap<String, Precedence>() {
            companion object {
                val LEFT = true
                val RIGHT = false
            }

            fun add(name: String, prec: Int, leftAssoc: Boolean) {
                put(name, Precedence(prec, leftAssoc))
            }
        }

        class Expr(clazz: KClass<out ASTree>?, val factor: Parser, val ops: Operators) : Element() {
            private val factory = Factory.getForASTList(clazz)
            override fun parse(lexer: Lexer, res: MutableList<ASTree>) {
                var right = factor.parse(lexer)
                var prec: Precedence? = nextOperator(lexer)
                while (prec != null) {
                    right = doShift(lexer, right, prec.value)
                    prec = nextOperator(lexer)
                }
                res.add(right)
            }

            override fun match(lexer: Lexer): Boolean = factor.match(lexer)

            private fun doShift(lexer: Lexer, left: ASTree, prec: Int): ASTree {
                val list: MutableList<ASTree> = mutableListOf(left, ASTLeaf(lexer.read()))
                var right = factor.parse(lexer)

                var next: Precedence? = nextOperator(lexer)
                while (next != null && rightIsExpr(prec, next)) {
                    right = doShift(lexer, right, next.value)
                }

                list.add(right)
                return factory.make(list)
            }

            private fun nextOperator(lexer: Lexer): Precedence? {
                val t = lexer.peek(0)
                return if (t.isIdentifier()) ops[t.getText()] else null
            }

            companion object {
                private fun rightIsExpr(prec: Int, nextPrec: Precedence): Boolean
                        = if (nextPrec.leftAssoc) prec < nextPrec.value else prec <= nextPrec.value
            }
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
                    var f = get(clazz, List::class)
                    return if (f != null) f else {
                        object : Factory() {
                            override fun make0(arg: Any): ASTree {
                                val results: List<ASTree> = arg as List<ASTree>
                                return if (results.size == 1) results.first() else ASTList(results)
                            }
                        }
                    }
                }

                fun get(clazz: KClass<out ASTree>?, argType: KClass<*>): Factory? {
                    if (clazz == null) {
                        return null
                    }
                    try {
                        val m = clazz.java.getMethod(factoryName, *listOf(argType.java).toTypedArray())
                        return object : Factory() {
                            override fun make0(arg: Any): ASTree {
                                return m.invoke(null, arg) as ASTree
                            }
                        }
                    } catch(e: NoSuchMethodException) {
                    }
                    try {
                        val c = clazz.java.getConstructor(argType.java)
                        return object : Factory() {
                            override fun make0(arg: Any): ASTree {
                                return c.newInstance(arg)
                            }
                        }
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

    fun match(lexer: Lexer): Boolean = if (elements.isEmpty()) true else elements.first().match(lexer)

    fun reset(): Parser {
        elements = mutableListOf()
        return this
    }

    fun reset(clazz: KClass<out ASTree>?): Parser {
        elements = mutableListOf()
        factory = Factory.getForASTList(clazz)
        return this
    }

    fun number(): Parser = number(null)

    fun number(clazz: KClass<out ASTLeaf>?): Parser {
        elements.add(NumToken(clazz))
        return this
    }

    fun identifier(reserved: HashSet<String>): Parser = identifier(null, reserved)

    fun identifier(clazz: KClass<out ASTLeaf>?, reserved: HashSet<String>): Parser {
        elements.add(IdToken(clazz, reserved))
        return this
    }

    fun string(): Parser = string(null)

    fun string(clazz: KClass<out ASTLeaf>?): Parser {
        elements.add(StrToken(clazz))
        return this
    }

    fun token(vararg pat: String): Parser {
        elements.add(Leaf(pat.asList()))
        return this
    }

    fun sep(vararg pat: String): Parser {
        elements.add(Skip(pat.asList()))
        return this
    }

    fun ast(p: Parser): Parser {
        elements.add(Tree(p))
        return this
    }

    fun or(vararg p: Parser): Parser {
        elements.add(OrTree(p.asList()))
        return this
    }

    fun maybe(p: Parser): Parser {
        val p2 = Parser(p)
        p2.reset()
        elements.add(OrTree(listOf(p, p2)))
        return this
    }

    fun option(p: Parser): Parser {
        elements.add(Repeat(p, true))
        return this
    }

    fun repeat(p: Parser): Parser {
        elements.add(Repeat(p, false))
        return this
    }

    fun expression(subexp: Parser, operators: Operators): Parser = expression(null, subexp, operators)

    fun expression(clazz: KClass<out ASTree>?, subexp: Parser, operators: Operators): Parser {
        elements.add(Expr(clazz, subexp, operators))
        return this
    }

    fun insertChoice(p: Parser): Parser {
        val e = elements[0]
        if (e is OrTree) {
            e.insert(p)
        } else {
            val otherwise = Parser(this)
            reset(null)
            or(p, otherwise)
        }
        return this
    }
}
