package jp.msfblue1.replindroid.lisp.eval;

/**
 * 評価器
 * 
 * @author tetsuya
 *
 */
public class Evaluator {
	/**
	 * <p>
	 * 引数の環境の下で引数のS式を評価する。
	 * </p>
	 * 
	 * <p>
	 * S式xの評価値をE[x]とすると、E[x]は次のように定義される。
	 * </p>
	 * <ul>
	 * <li>xが整数値の場合、E[x] = x</li>
	 * <li>xが真理値の場合、E[x] = x</li>
	 * <li>xが空リストの場合、E[x] = 空リスト</li>
	 * <li>xが未定義値の場合、E[x] = 未定義値</li>
	 * <li>xがクロージャの場合、E[x] = x</li>
	 * <li>xがサブルーチンの場合、E[x] = x</li>
	 * <li>xが特殊形式の場合、E[x] = x</li>
	 * <li>xが記号の場合、E[x] = 環境の下で記号xに束縛された値</li>
	 * <li>xが空リストではないリスト(x0 x1 ... xn)の場合
	 * <ul>
	 * <li>E[x0]が特殊形式の場合、特殊形式を引数x1, ..., xn に適用した結果</li>
	 * <li>E[x0]がサブルーチン（組み込み手続き）の場合、サブルーチンを引数E[x1], ..., E[xn]に適用した結果</li>
	 * <li>E[x0]がクロージャの場合、クロージャを引数E[x1], ..., E[xn]に適用した結果</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @param sexp S式
	 * @param env  環境
	 * @return 評価値(S式)
	 */
	public static SExpression eval(SExpression sexp, Environment env) {
		if (sexp instanceof Str){
			return sexp;
		}
		if (sexp instanceof Bool){
			return sexp;
		}
		if (sexp instanceof EmptyList) {
			return sexp;
		}
		if (sexp instanceof java.lang.Number) {
			return sexp;
		}
		if (sexp instanceof Callable){
			return sexp;
		}
		if (sexp instanceof Symbol) {
			return env.getValueOf((Symbol) sexp);
		}

		if (sexp instanceof ConsCell){
			ConsCell cell = (ConsCell) sexp;
			SExpression applied = eval(cell.getCar(),env);
			if(applied instanceof Closure){
				SExpression appliedArgs = Evaluator.evalArguments(cell.getCdr(),env);
				return ((Closure)applied).apply(env,appliedArgs);
			}
			if(applied instanceof SpecialForm){
				return ((SpecialForm)applied).apply(env,cell.getCdr());
			}
			if(applied instanceof Subroutine){
				SExpression appliedArgs = Evaluator.evalArguments(cell.getCdr(),env);
				return ((Subroutine)applied).apply(env,appliedArgs);
			}
		}

		return sexp;
	}

	private static SExpression evalArguments(SExpression arg,Environment env){
		if(arg instanceof ConsCell){
			ConsCell argCell = (ConsCell) arg;
			return ConsCell.getInstance(
					eval(argCell.getCar(),env),
					evalArguments(argCell.getCdr(),env));
		}
		return eval(arg,env);
	}
}
