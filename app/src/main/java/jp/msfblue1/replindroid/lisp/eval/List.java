package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class List implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env, SExpression args) {
        if (args instanceof EmptyList) {
            return args;
        }
        if (args instanceof ConsCell) {
            return ConsCell.getInstance(((ConsCell) args).getCar(), apply(env,((ConsCell) args).getCdr()));
        }
        throw new SyntaxErrorException("malformed list");
    }
}
