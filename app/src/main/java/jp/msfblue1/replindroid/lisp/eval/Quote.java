package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class Quote implements SpecialForm {

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env, SExpression argument) {
		if (argument instanceof ConsCell) {
			if (!(((ConsCell) argument).getCdr() instanceof EmptyList)) {
				throw new SyntaxErrorException("malformed quote");
			}
			return ((ConsCell) argument).getCar();
		} else {
			throw new SyntaxErrorException("malformed quote");
		}
	}

	@Override
	public String toString() {
		return "#<syntax quote>";
	}
}
