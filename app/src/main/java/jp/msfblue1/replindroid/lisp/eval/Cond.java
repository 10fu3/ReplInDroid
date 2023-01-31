package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.LinkedList;

public class Cond implements SpecialForm {

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env, SExpression argument) {
		if(!(argument instanceof ConsCell)){
			throw new ArgumentException("");
		}
		LinkedList<SExpression> list = (LinkedList)ConsCell.toList(argument);

		ConsCell pair = null;
		ConsCell result = null;

		for(SExpression element : list){
			if(!(element instanceof ConsCell)){
				throw new TypeException("");
			}
			pair = (ConsCell) element;

			if(!(pair.getCdr() instanceof ConsCell) || !(((ConsCell)pair.getCdr()).getCdr() instanceof EmptyList)){
				throw new TypeException("");
			}

			result = (ConsCell) pair.getCdr();

			if(list.getLast() == element && Symbol.getInstance("else").equals(pair.getCar())){
				return Evaluator.eval(result.getCar(),env);
			}

			if(!Bool.valueOf(false).equals(Evaluator.eval(pair.getCar(),env))){
				return Evaluator.eval(result.getCar(),env);
			}
		}

		return Undef.getInstance();
	}

	@Override
	public String toString() {
		return "#<syntax #cond>";
	}
}
