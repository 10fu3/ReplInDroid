package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.LispException;
import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class Cos implements Subroutine {

	@Override
	public SExpression apply(Environment env,SExpression args) {
		try {
			if (!(((ConsCell) args).getCdr() instanceof EmptyList)) {
				throw new SyntaxErrorException("malformed cos");
			}
			return ((Number) ((ConsCell) args).getCar()).cos();
		} catch (ClassCastException e) {
		}
		throw new SyntaxErrorException("malformed cos");
	}

	@Override
	public String toString() {
		return "#<subr cos>";
	}

	@Override
	public boolean isList() {
		return false;
	}
}
