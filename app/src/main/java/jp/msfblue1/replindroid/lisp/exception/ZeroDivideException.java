package jp.msfblue1.replindroid.lisp.exception;

/**
 * ゼロ除算Exception
 */
public class ZeroDivideException extends LispException{

    /**
     * エラーメッセージを持つ例外を構築する。
     *
     * @param msg エラーメッセージ
     */
    public ZeroDivideException(String msg) {
        super("Zero divide exception: "+msg);
    }
}
