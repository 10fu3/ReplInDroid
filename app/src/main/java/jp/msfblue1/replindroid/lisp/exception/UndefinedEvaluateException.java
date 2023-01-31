package jp.msfblue1.replindroid.lisp.exception;

/**
 * 未定義な変数を評価する際に起きるException
 */
public class UndefinedEvaluateException extends LispException{

    /**
     * エラーメッセージを持つ例外を構築する。
     *
     * @param msg エラーメッセージ
     */
    public UndefinedEvaluateException(String msg) {
        super("UndefinedEvaluateException: "+msg);
    }
}
