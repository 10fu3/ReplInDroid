package jp.msfblue1.replindroid.lisp.eval;

/**
 * 特殊形式
 * @author tetsuya
 *
 */
public interface SpecialForm extends SExpression , Callable {
    SExpression apply(Environment env,SExpression argument);
}
