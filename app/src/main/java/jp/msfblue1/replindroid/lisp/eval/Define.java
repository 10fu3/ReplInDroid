package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.LinkedList;

public class Define implements SpecialForm {

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public SExpression apply(Environment env, SExpression argument) {

		if(!(argument instanceof ConsCell)){
			throw new TypeException("argument isn't ConsCell");
		}

		ConsCell cellArg = (ConsCell) argument;

		if(!(cellArg.getCar() instanceof Symbol)){
			if(cellArg.getCar() instanceof ConsCell){
				return onList(env,cellArg);
			}
			throw new TypeException("1st argument isn't Symbol");
		}

		return onSymbol(env,cellArg);
	}

	private SExpression onSymbol(Environment env, ConsCell argument){
		Symbol sexpSymbol = (Symbol) argument.getCar();

		if(!(argument.getCdr() instanceof ConsCell)){
			throw new ArgumentException("argument size must be 2");
		}

		ConsCell sexpCell = (ConsCell) argument.getCdr();

		if(!(sexpCell.getCdr() instanceof EmptyList)){
			throw new ArgumentException("argument size must be 2");
		}

		env.define(sexpSymbol,Evaluator.eval(sexpCell.getCar(),env));

		return sexpSymbol;
	}

	private SExpression onList(Environment env, ConsCell args){

		if(!(args.getCar() instanceof ConsCell)){
			throw new TypeException("");
		}

		if(!(args.getCdr() instanceof ConsCell)){
			throw new TypeException("");
		}

		SExpression nameAndFormals = args.getCar();

		if(!(nameAndFormals instanceof ConsCell)){
			throw new TypeException("");
		}

		SExpression name = ((ConsCell) nameAndFormals).getCar();
		SExpression formals = ((ConsCell) nameAndFormals).getCdr();

		SExpression body = ((ConsCell) args.getCdr()).getCar();

		if(!(name instanceof Symbol)){
			throw new TypeException("");
		}

		env.define((Symbol) name,
				new Closure(
						formals,
						body,
						env
				));

		return name;
	}

	@Override
	public String toString() {
		return "#<syntax define>";
	}
}
