package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.LispException;
import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class Sin implements Subroutine {

	@Override
	public SExpression apply(Environment env,SExpression args) {
		try {
			if (!(((ConsCell) args).getCdr() instanceof EmptyList)) {
				throw new SyntaxErrorException("malformed sin");
			}
			return ((Number) ((ConsCell) args).getCar()).sin();
		} catch (ClassCastException e) {
		}
		throw new SyntaxErrorException("malformed sin");
	}

	@Override
	public String toString() {
		return "#<subr sin>";
	}

	@Override
	public boolean isList() {
		return false;
	}

}
