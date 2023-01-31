package jp.msfblue1.replindroid.lisp.eval;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 記号
 * 
 * @author tetsuya
 *
 */
public class Symbol implements SExpression,Atom {

	private static java.util.Map<String,Symbol> cachedSymbol = new ConcurrentHashMap<>();

	private String name;

	public String getName() {
		return name;
	}

	private Symbol(String symbol) {
		this.name = symbol;
	}

	/**
	 * 記号のインスタンスを得る。
	 * 
	 * @param name 記号名
	 * @return 記号のインスタンス
	 */
	public static Symbol getInstance(String name) {
		Symbol symbol = cachedSymbol.get(name);
		if(symbol != null){
			return cachedSymbol.get(name);
		}
		cachedSymbol.put(name,new Symbol(name));
		return getInstance(name);
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Symbol) {
			return this.name.equals(((Symbol) obj).getName());
		}
		return false;
	}

	@Override
	public boolean isList() {
		return false;
	}
}
