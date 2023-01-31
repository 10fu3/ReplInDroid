package jp.msfblue1.replindroid.lisp.eval;

/**
 * 四則演算ができることを定義するインターフェース
 */
public interface Number extends SExpression,Comparable<Number>,Atom {
    Number add(Number number);
    Number minus(Number number);
    Number multiply(Number number);
    Number divide(Number number);
    Number mod(Number number);

    Number sin();
    Number cos();
    Number tan();
}
