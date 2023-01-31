package jp.msfblue1.replindroid.lisp.eval;

public class Exit implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {
        System.exit(0);
        return Undef.getInstance();
    }
}
