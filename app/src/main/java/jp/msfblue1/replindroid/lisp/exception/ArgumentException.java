package jp.msfblue1.replindroid.lisp.exception;

/**
 * 引数が足りないときのエラー
 */
public class ArgumentException extends LispException{
    /**
     * エラーメッセージを持つ例外を構築する。
     *
     * @param msg エラーメッセージ
     */
    public ArgumentException(String msg) {
        super("ArgumentException: "+msg);
    }
}
