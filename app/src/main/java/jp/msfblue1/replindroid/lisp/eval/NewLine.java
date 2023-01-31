package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class NewLine implements Subroutine {
	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env,SExpression argument) throws SyntaxErrorException {
		if(argument instanceof EmptyList) {
			return Str.valueOf(new StringBuilder("\n"));
		}
		throw new SyntaxErrorException("");
	}

	public String toString() {
		return "#<subr newline>";
	}
}
