package jp.msfblue1.replindroid.lisp.eval;

import java.lang.Float;
import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class Sqrt implements Subroutine{

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env, SExpression args) {
        try {
            if (!(((ConsCell) args).getCdr() instanceof EmptyList)) {
                throw new SyntaxErrorException("malformed sin");
            }
            String valStr = ((Number) ((ConsCell) args).getCar()).toString();
            double sqrt = Math.sqrt(Double.parseDouble(valStr));
            return jp.msfblue1.replindroid.lisp.eval.Float.valueOf(Float.parseFloat(String.valueOf(sqrt)));
        } catch (ClassCastException e) {
        }
        throw new SyntaxErrorException("malformed sin");
    }

    @Override
    public String toString() {
        return "#<subr sqrt>";
    }
}
