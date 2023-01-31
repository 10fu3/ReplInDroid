package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class Set implements SpecialForm {

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env, SExpression argument) {
		if (!(argument instanceof ConsCell)) {
			throw new SyntaxErrorException("malformed set!)");
		}
		ConsCell cellArgu = (ConsCell) argument;

		if (!(cellArgu.getCdr() instanceof ConsCell)) {
			throw new SyntaxErrorException("malformed set!)");
		}
		if (!(((ConsCell) cellArgu.getCdr()).getCdr() instanceof EmptyList)) {
			throw new SyntaxErrorException("malformed set!)");
		}
		Symbol symbol = (Symbol) cellArgu.getCar();
		SExpression sexp = Evaluator.eval(((ConsCell) cellArgu.getCdr()).getCar(),env);

		env.set(symbol, sexp);

		return sexp;
	}

	@Override
	public String toString() {
		return "#<syntax set!>";
	}
}
