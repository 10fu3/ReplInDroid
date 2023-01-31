package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class IsNull implements Subroutine {
	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env,SExpression argument) {
		try {
			if (!(((ConsCell) argument).getCdr() instanceof EmptyList)) {
				throw new SyntaxErrorException("nullの引数は１つ");
			}
			return Bool.valueOf(((ConsCell) argument).getCar() instanceof EmptyList);
		} catch (ClassCastException e) {
		}
		throw new SyntaxErrorException("nullの引数は１つ");
	}

	@Override
	public String toString() {
		return "#<subr null?>";
	}

}
