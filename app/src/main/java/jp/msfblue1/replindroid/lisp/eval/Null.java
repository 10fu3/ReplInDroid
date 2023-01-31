package jp.msfblue1.replindroid.lisp.eval;

public class Null implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {
        return null;
    }
}
