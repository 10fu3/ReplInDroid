package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.UUID;

/**
 * クロージャ
 *
 * @author tetsuya
 *
 */
public class Closure implements Callable {

	private final SExpression body;

	private final SExpression formals;
	private final Environment env;

	private String uuid = UUID.randomUUID().toString();

	@Override
	public boolean isList() {
		return false;
	}

	public SExpression apply(Environment environment,SExpression args){

		SExpression loopFormals = this.formals;
		SExpression loopArgs = args;

		Environment env = new Environment(this.env);

		do {
			if(loopFormals instanceof EmptyList){
				if(loopArgs instanceof EmptyList){
					break;
				}
				throw new ArgumentException("argument size more than formals");
			}
			if(loopFormals instanceof Symbol){
				env.define((Symbol) loopFormals,loopArgs);
				break;
			}
			if(loopFormals instanceof ConsCell){
				ConsCell cellFormals = (ConsCell) loopFormals;
				if(!((cellFormals.getCar() instanceof Symbol))){
					throw new TypeException(cellFormals.getCar().toString());
				}
				if(!(loopArgs instanceof ConsCell)){
					throw new ArgumentException("argument size less than formals");
				}
				ConsCell cellArgs = (ConsCell) loopArgs;
				env.define((Symbol) cellFormals.getCar(),cellArgs.getCar());
				loopFormals = cellFormals.getCdr();
				loopArgs = cellArgs.getCdr();
			}

		}while (true);

		return Evaluator.eval(this.body,env);
	}

	@Override
	public String toString() {
		return "#<closure "+uuid+">";
	}

	public Closure(SExpression formals, SExpression body, Environment environment){
		this.formals = formals;
		this.body = body;
		this.env = new Environment(environment);
	}
}