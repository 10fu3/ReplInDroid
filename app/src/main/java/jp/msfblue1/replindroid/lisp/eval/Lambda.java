package jp.msfblue1.replindroid.lisp.eval;

public class Lambda implements SpecialForm {

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env, SExpression argument) {
		SExpression params = ((ConsCell)argument).getCar(); // 引数
		SExpression body = ((ConsCell)((ConsCell)argument).getCdr()).getCar(); // 手続き本体

		return new Closure(params,body,env);

	}

	@Override
	public String toString() {
		return "#<syntax lambda>";
	}
}
