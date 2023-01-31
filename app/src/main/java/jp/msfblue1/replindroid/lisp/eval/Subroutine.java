package jp.msfblue1.replindroid.lisp.eval;


/**
 * 組み込み手続き
 * 
 * @author tetsuya
 *
 */
public interface Subroutine extends SExpression , Callable {
	SExpression apply(Environment env,SExpression argument);
}
