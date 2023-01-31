package jp.msfblue1.replindroid.lisp.reader;

import java.io.BufferedReader;
import java.io.IOException;

import jp.msfblue1.replindroid.lisp.eval.SExpression;
import jp.msfblue1.replindroid.lisp.eval.*;
import jp.msfblue1.replindroid.lisp.eval.Float;
import jp.msfblue1.replindroid.lisp.exception.LispException;
import jp.msfblue1.replindroid.lisp.exception.SyntaxErrorException;

/**
 * 構文解析器
 * 
 * @author tetsuya
 *
 */
public class Reader {
	/**
	 * 字句解析器
	 */
	private Lexer lexer;

	/**
	 * 先読みの字句
	 */
	private Token token = null;

	/**
	 * 括弧の入れ子レベル。式を読み終わった時にnestingLevelが0ならば、そこまでの式を評価する。
	 */
	private int nestingLevel = 0;

	/**
	 * コンストラクタ
	 * 
	 * @param in 入力
	 */
	public Reader(BufferedReader in) {
		this.lexer = new Lexer(in);
	}

	/**
	 * <pre>
	 * {@literal <S式>} ::= {@literal <整数値>} | {@literal <浮動小数点値>} | {@literal <記号>} | {@literal <真理値>} | '(' ({@literal <S式>} '.' {@literal <S式>})? ')'
	 * </pre>
	 * 
	 * @return S式
	 * @throws LispException
	 * @throws IOException
	 */
	public static final Symbol QUOTE = Symbol.getInstance("quote");

	SExpression sExpression() throws IOException, LispException {
		// 整数値
		if (this.token.getKind() == Token.Kind.NUMBER) {
			int value = this.token.getIntValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Int.valueOf(value);
		}
		// 整数値
		if (this.token.getKind() == Token.Kind.FLOAT) {
			float value = this.token.getFloatValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Float.valueOf(value);
		}

		if (this.token.getKind() == Token.Kind.STRING) {
			StringBuilder sb = this.token.getStringValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Str.valueOf(sb);
		}

		// 記号
		if (this.token.getKind() == Token.Kind.SYMBOL) {
			String value = this.token.getSymbolValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Symbol.getInstance(value);
		}
		// 真理値
		if (this.token.getKind() == Token.Kind.BOOLEAN) {
			boolean value = this.token.getBooleanValue();
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return Bool.valueOf(value);
		}
		// クォート
		if (this.token.getKind() == Token.Kind.QUOTE) {
			this.token = this.lexer.getNextToken();
			return ConsCell.getInstance(QUOTE, ConsCell.getInstance(sExpression(), EmptyList.getInstance()));
		}
		// '(' ')' or '(' <S式> . <S式> ')'
		if (this.token.getKind() == Token.Kind.LPAREN) {
			this.nestingLevel++;
			this.token = this.lexer.getNextToken();
			// 空リスト
			if (this.token.getKind() == Token.Kind.RPAREN) {
				this.nestingLevel--;
				if (this.nestingLevel != 0) { // 式が未完成
					this.token = this.lexer.getNextToken();
				}
				return EmptyList.getInstance();
			}
			// car
			SExpression car = sExpression();

			// cdr
			SExpression cdr = getCdr();

			this.nestingLevel--;
			if (this.nestingLevel != 0) { // 式が未完成
				this.token = this.lexer.getNextToken();
			}
			return ConsCell.getInstance(car, cdr);
		}
		throw new SyntaxErrorException("Invalid expression:" + this.token.getKind());
	}

	SExpression getCdr() throws IOException, LispException {
		if (this.token.getKind() == Token.Kind.RPAREN) {
			return EmptyList.getInstance();
		}

		if (this.token.getKind() == Token.Kind.DOT) {
			this.token = this.lexer.getNextToken();
			return sExpression();
		}
		return ConsCell.getInstance(sExpression(), getCdr());
	}

	public SExpression read() throws IOException, LispException {
		this.nestingLevel = 0;
		this.token = this.lexer.getNextToken();
		return sExpression();
	}
}
