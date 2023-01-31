package jp.msfblue1.replindroid.lisp.exception;

/**
 * プログラムの誤りを表す例外
 * 
 * @author tetsuya
 *
 */
@SuppressWarnings("serial")
public class LispException extends RuntimeException {
	/**
	 * エラーメッセージを持つ例外を構築する。
	 * 
	 * @param msg エラーメッセージ
	 */
	public LispException(String msg) {
		super(msg);
	}
}
