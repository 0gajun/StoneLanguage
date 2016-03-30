package stone.ast

import stone.StoneConst
import stone.env.Environment
import stone.exception.StoneException
import stone.firstclass.StoneObject

/**
 * 二項演算子を表す節
 *
 * Created by Junya on 2016/03/19.
 */
class BinaryExpr(children: List<ASTree>) : ASTList(children) {

    fun left(): ASTree = child(0)
    fun operator(): String = (child(1) as ASTLeaf).token.getText()
    fun right(): ASTree = child(2)

    override fun eval(env: Environment): Any {
        val op = operator()

        if ("=".equals(op)) {
            // if assignment
            val rvalue = right().eval(env)
            return computeAssign(env, rvalue)
        }

        return computeOp(left().eval(env), op, right().eval(env))
    }

    private fun computeAssign(env: Environment, rvalue: Any): Any {
        val l = left()

        if (l is PrimaryExpr && l.hasPostfix(0) && l.postfix(0) is Dot) {
            // left side is some class object's member
            val t = l.evalSubExpr(env, 1)
            if (t is StoneObject) {
                return setField(t, l.postfix(0) as Dot, rvalue)
            }
        } else if (l is Name) {
            env.put(l.name(), rvalue)
            return rvalue
        }

        throw StoneException("bad assignment", this)
    }

    private fun setField(obj: StoneObject, expr: Dot, rvalue: Any): Any {
        try {
            obj.write(expr.name(), rvalue)
            return rvalue
        } catch (e: StoneObject.AccessException) {
            throw StoneException("bad member access: " + location() + ": " + expr.name())
        }
    }

    private fun computeOp(leftValue: Any, op: String, rightValue: Any): Any {
        if (leftValue is Int && rightValue is Int) {
            return computeNumber(leftValue, op, rightValue)
        }

        return when (op) {
            "+" -> leftValue.toString() + rightValue.toString()
            "==" -> if (leftValue.equals(rightValue)) StoneConst.TRUE else StoneConst.FALSE
            else -> throw StoneException("bad type", this)
        }
    }

    private fun computeNumber(left: Int, op: String, right: Int) = when (op) {
        "+" -> left + right
        "-" -> left - right
        "*" -> left * right
        "/" -> left / right
        "%" -> left % right
        "==" -> if (left == right) StoneConst.TRUE else StoneConst.FALSE
        ">" -> if (left > right) StoneConst.TRUE else StoneConst.FALSE
        "<" -> if (left < right) StoneConst.TRUE else StoneConst.FALSE
        else -> throw StoneException("bad operator: " + toString(), this)
    }
}
