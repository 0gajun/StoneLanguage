package stone.parser

import stone.ast.*
import stone.lexer.Lexer
import stone.parser.Parser.rule
import stone.parser.Parser.Operators
import stone.token.Token
import java.util.*

/**
 * 構文解析器
 *
 * Created by Junya on 2016/03/19.
 */
open class BasicParser {
    protected val reserved: HashSet<String> = hashSetOf(";", "}", Token.EOL)
    private val operators: Operators = Operators()

    init {
        initOperators()
    }

    protected val expr0: Parser = rule()

    protected val primary: Parser = rule(PrimaryExpr::class.java)
            .or(rule().sep("(").ast(expr0).sep(")"),
                    rule().number(NumberLiteral::class.java),
                    rule().identifier(Name::class.java, reserved),
                    rule().string(StringLiteral::class.java))

    protected val factor: Parser = rule()
            .or(rule(NegativeExpr::class.java).sep("-").ast(primary), primary)

    protected val expr: Parser = expr0.expression(BinaryExpr::class.java, factor, operators)

    protected val statement0: Parser = rule()

    protected val block: Parser = rule(BlockStmnt::class.java).sep("{").option(statement0)
            .repeat(rule().sep(";", Token.EOL).option(statement0))
            .sep("}")

    protected val simple: Parser = rule(PrimaryExpr::class.java).ast(expr)

    protected val statement: Parser = statement0
            .or(rule(IfStmnt::class.java).sep("if").ast(expr).ast(block)
                    .option(rule().sep("else").ast(block)),
                    rule(WhileStmnt::class.java).sep("while").ast(expr).ast(block),
                    simple)

    protected val program: Parser = rule().or(statement, rule(NullStmnt::class.java)).sep(";", Token.EOL)

    fun parse(lexer: Lexer) : ASTree = program.parse(lexer)

    fun initOperators() {
        operators.add("=", 1, Operators.RIGHT)
        operators.add("==", 2, Operators.LEFT)
        operators.add(">", 2, Operators.LEFT)
        operators.add("<", 2, Operators.LEFT)
        //operators.add("!=", 2, Operators.LEFT)
        //operators.add("<=", 2, Operators.LEFT)
        //operators.add(">=", 2, Operators.LEFT)
        operators.add("+", 3, Operators.LEFT)
        operators.add("-", 3, Operators.LEFT)
        operators.add("*", 4, Operators.LEFT)
        operators.add("/", 4, Operators.LEFT)
        operators.add("%", 4, Operators.LEFT)
    }
}
