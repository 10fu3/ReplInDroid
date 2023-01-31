package jp.msfblue1.replindroid.lisp.eval;

public interface Callable extends SExpression {
    SExpression apply(Environment environment,SExpression args);
}
