package jp.msfblue1.replindroid.lisp.reader;

/**
 * 字句
 * 
 * @author tetsuya
 *
 */
public class Token {
	/**
	 * 字句の種類
	 * 
	 * @author tetsuya
	 *
	 */
	public enum Kind {
		NUMBER, // 数値
		FLOAT, // 浮動小数点
		BOOLEAN, // 真理値
		STRING, //文字列
		SYMBOL, // 記号
		LPAREN, // 左括弧
		RPAREN, // 右括弧
		DOT, // ドット
		QUOTE // シングルクォート
	}

	private Kind kind;
	private int intValue;

	private float floatValue;
	private boolean booleanValue;
	private String symbol;

	private StringBuilder string;

	/**
	 * 字句の種類を得る。
	 * 
	 * @return 字句の種類
	 */
	Kind getKind() {
		return this.kind;
	}

	/**
	 * 整数値を得る。
	 * 
	 * @return 整数値
	 */
	int getIntValue() {
		return this.intValue;
	}

	/**
	 * 浮動小数点を得る
	 * 
	 * @return 浮動小数点数
	 */
	float getFloatValue() {
		return this.floatValue;
	}

	/**
	 * 真理値を得る。
	 * 
	 * @return 真理値
	 */
	boolean getBooleanValue() {
		return this.booleanValue;
	}

	/**
	 * 記号名を得る。
	 * 
	 * @return 記号名
	 */
	String getSymbolValue() {
		return this.symbol;
	}

	/**
	 * 文字列値を得る
	 *
	 * @return 文字列値
	 */
	StringBuilder getStringValue() {
		return this.string;
	}

	/**
	 * 種類だけを指定して字句を構築する。
	 * 
	 * @param kind 字句の種類
	 */
	Token(Kind kind) {
		this.kind = kind;
	}

	/**
	 * 整数の字句
	 * 
	 * @param value 整数値
	 */
	Token(int value) {
		this.kind = Kind.NUMBER;
		this.intValue = value;
	}

	/**
	 * 浮動小数点数の字句
	 *
	 * @param value 浮動小数点数
	 */
	Token(float value) {
		this.kind = Kind.FLOAT;
		this.floatValue = value;
	}

	/**
	 * 真理値の字句
	 * 
	 * @param value 真理値
	 */
	Token(boolean value) {
		this.kind = Kind.BOOLEAN;
		this.booleanValue = value;
	}

	/**
	 * 記号の字句
	 * 
	 * @param value 記号名
	 */
	Token(String value) {
		this.kind = Kind.SYMBOL;
		this.symbol = value;
	}

	/**
	 * 文字列の字句
	 *
	 * @param value 文字列値
	 */
	Token(StringBuilder value) {
		this.kind = Kind.STRING;
		this.string = value;
	}

	@Override
	public String toString() {
		// 数値
		if (this.kind == Kind.NUMBER) {
			return "Token (Number, " + this.intValue + ")";
		}
		// 浮動小数点数
		if (this.kind == Kind.FLOAT) {
			return "Token (Float, " + this.floatValue + ")";
		}
		// 真理値
		if (this.kind == Kind.BOOLEAN) {
			return "Token (Boolean, " + this.booleanValue + ")";
		}
		// 記号
		if (this.kind == Kind.SYMBOL) {
			return "Token (Symbol, " + this.symbol + ")";
		}
		// 文字列値
		if (this.kind == Kind.STRING) {
			return "Token (String, " + this.string + ")";
		}
		// 左括弧
		if (this.kind == Kind.LPAREN) {
			return "Token (LeftPar)";
		}
		// 右括弧
		if (this.kind == Kind.RPAREN) {
			return "Token (RightPar)";
		}
		// ドット
		if (this.kind == Kind.DOT) {
			return "Token (Dot)";
		}
		// クォート
		if (this.kind == Kind.QUOTE) {
			return "Token (Quote)";
		}
		return "Token (Unknown)";
	}
}
