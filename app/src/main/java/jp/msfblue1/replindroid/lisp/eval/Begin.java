package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.List;

public class Begin implements SpecialForm {

	@Override
	public boolean isList() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SExpression apply(Environment env, SExpression argument) {
		try{

			List<SExpression> args = ConsCell.toList(argument);

			SExpression last = Undef.getInstance();

			for(SExpression arg : args){
				last = Evaluator.eval(arg,env);
			}

			return last;

		}catch (ClassCastException e){}
		throw new TypeException("argument type must be ConsCell");
	}

	@Override
	public String toString() {
		return "";
	}
}
