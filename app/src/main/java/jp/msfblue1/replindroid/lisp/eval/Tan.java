package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.LispException;
import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class Tan implements Subroutine {
	@Override
	public SExpression apply(Environment env,SExpression args) {
		try {
			if (!(((ConsCell) args).getCdr() instanceof EmptyList)) {
				throw new SyntaxErrorException("malformed tan");
			}
			return ((Number) ((ConsCell) args).getCar()).tan();
		} catch (ClassCastException e) {
		}
		throw new SyntaxErrorException("malformed tan");
	}

	@Override
	public String toString() {
		return "#<subr tan>";
	}

	@Override
	public boolean isList() {
		return false;
	}
}
