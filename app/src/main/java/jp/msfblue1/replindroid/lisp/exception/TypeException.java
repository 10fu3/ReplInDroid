package jp.msfblue1.replindroid.lisp.exception;

/**
 * 型の不一致によるエラー
 * (+ 1 'a') <- Exception
 */
public class TypeException extends LispException{

    /**
     * エラーメッセージを持つ例外を構築する。
     *
     * @param msg エラーメッセージ
     */
    public TypeException(String msg) {
        super("TypeException: "+msg);
    }
}
