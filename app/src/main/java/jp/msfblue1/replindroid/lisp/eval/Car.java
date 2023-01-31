package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

public class Car implements Subroutine {
	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env,SExpression argument) throws SyntaxErrorException {

		if(argument instanceof EmptyList){
			throw new ArgumentException("1st argument required, but we got 0.");
		}

		if(!(argument instanceof ConsCell)){
			throw new SyntaxErrorException("malformed car");
		}

		ConsCell args = (ConsCell)argument;

		//2個以上の引数があればエラーとする
		SExpression tailArgument = args.getCdr();
		if(!(tailArgument instanceof EmptyList)){
			throw new ArgumentException("1st argument required, but we got more 1.");
		}

		if(!((args.getCar()) instanceof ConsCell)){
			throw new SyntaxErrorException("malformed car");
		}

		return ((ConsCell)args.getCar()).getCar();
	}

	@Override
	public String toString() {
		return "#<subr car>";
	}

}
