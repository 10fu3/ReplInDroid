package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class Cdr implements Subroutine {

	@Override
	public String toString() {
		return "#<subr cdr>";
	}

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env,SExpression argument) throws SyntaxErrorException {
		try {
			if (!(((ConsCell) argument).getCdr() instanceof EmptyList)) {
				throw new SyntaxErrorException("不正なアクセス");
			}
			return ((ConsCell) ((ConsCell) argument).getCar()).getCdr();
		} catch (ClassCastException e) {
		}
		throw new SyntaxErrorException("不正なアクセス");
	}
}
