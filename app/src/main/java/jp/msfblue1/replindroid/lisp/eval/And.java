package jp.msfblue1.replindroid.lisp.eval;

public class And implements SpecialForm {

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env, SExpression argument) {

		if(argument instanceof EmptyList){
			return Bool.valueOf(true);
		}

		java.util.List<SExpression> args = ConsCell.toList(argument);

		SExpression evaluated = EmptyList.getInstance();

		for (SExpression item : args) {
			evaluated = Evaluator.eval(item, env);
			if (evaluated.equals(Bool.valueOf(false))) {
				return evaluated;
			}
		}
		return evaluated;
	}

	@Override
	public String toString() {
		return "#<syntax #and>";
	}
}
