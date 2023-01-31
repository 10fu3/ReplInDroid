package jp.msfblue1.replindroid.lisp.eval;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import jp.msfblue1.replindroid.lisp.exception.UndefinedEvaluateException;

/**
 * 環境
 * 
 * @author tetsuya
 *
 */

public class Environment {

	private Map<Symbol,SExpression> frame = new HashMap<>();
	private Environment parent = null;

	/**
	 * 現在のフレームからみて親のフレームの参照をセットする
	 * 
	 * @param parent
	 */
	public Environment(Environment parent){
		this.parent = parent;
	}

	/**
	 * 変数（シンボル）が束縛されている値を返す。
	 * 
	 * @param symbol シンボル
	 * @return シンボルが束縛されている値
	 */
	public SExpression getValueOf(Symbol symbol) throws UndefinedEvaluateException {
		if(frame.containsKey(symbol)){
			return this.frame.get(symbol);
		}
		if(this.parent == null){
			throw new UndefinedEvaluateException(symbol.getName());
		}
		return this.parent.getValueOf(symbol);
	}

	/**
	 * 変数束縛
	 * 
	 * @param symbol シンボル
	 * @param sexp   束縛する値
	 */
	public void define(Symbol symbol, SExpression sexp) {
		this.frame.put(symbol,sexp);
	}

	/**
	 * 変数の値の再定義
	 * 
	 * @param symbol シンボル
	 * @param sexp   束縛する値
	 */
	public void set(Symbol symbol, SExpression sexp) throws UndefinedEvaluateException {
		if(this.frame.containsKey(symbol)){
			this.frame.put(symbol,sexp);
			return;
		}

		if(parent == null){
			throw new UndefinedEvaluateException(symbol.getName());
		}

		this.parent.set(symbol,sexp);
	}

	public static Environment createGlobalEnv(){
		Environment env = new Environment(null);
		env.define(Symbol.getInstance("+"), new Add());
		env.define(Symbol.getInstance("-"), new Minus());
		env.define(Symbol.getInstance("*"), new Multiply());
		env.define(Symbol.getInstance("/"), new Divide());
		env.define(Symbol.getInstance("modulo"), new Modulo());
		env.define(Symbol.getInstance("define"), new Define());
		env.define(Symbol.getInstance("car"), new Car());
		env.define(Symbol.getInstance("cdr"), new Cdr());
		env.define(Symbol.getInstance("lambda"), new Lambda());
		env.define(Symbol.getInstance("quote"), new Quote());
		env.define(Symbol.getInstance("map"),new jp.msfblue1.replindroid.lisp.eval.Map());
		env.define(Symbol.getInstance("write"),new Write());
		env.define(Symbol.getInstance("="),new Equals());
		env.define(Symbol.getInstance("<"),new Smaller());
		env.define(Symbol.getInstance("<="),new OrMore());
		env.define(Symbol.getInstance(">"),new Bigger());
		env.define(Symbol.getInstance(">="),new OrLess());
		env.define(Symbol.getInstance("eq?"),new IsEq());
		env.define(Symbol.getInstance("equal?"),new IsEqual());
		env.define(Symbol.getInstance("null?"),new IsNull());
		env.define(Symbol.getInstance("not"),new Not());
		env.define(Symbol.getInstance("and"),new And());
		env.define(Symbol.getInstance("or"),new Or());
		env.define(Symbol.getInstance("cond"),new Cond());
		env.define(Symbol.getInstance("|>"),new Pipe());
		env.define(Symbol.getInstance("if"),new If());
		env.define(Symbol.getInstance("set!"),new Set());
		env.define(Symbol.getInstance("exit"),new Exit());
		env.define(Symbol.getInstance("begin"),new Begin());
		env.define(Symbol.getInstance("read-file"),new ReadInLisp());
		env.define(Symbol.getInstance("new-line"),new NewLine());
		env.define(Symbol.getInstance("let"),new Let());
		env.define(Symbol.getInstance("sin"),new Sin());
		env.define(Symbol.getInstance("cos"),new Cos());
		env.define(Symbol.getInstance("tan"),new Tan());
		env.define(Symbol.getInstance("cons"),new Cons());
		env.define(Symbol.getInstance("list"),new List());
		env.define(Symbol.getInstance("append"),new Append());
		env.define(Symbol.getInstance("sqrt"),new Sqrt());
		env.define(Symbol.getInstance("assoc"),new Assoc());
		env.define(Symbol.getInstance("apply"),new Apply());
		env.define(Symbol.getInstance("cut"),new Cut());
		return env;
	}
}
