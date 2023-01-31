package jp.msfblue1.replindroid.lisp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.msfblue1.replindroid.lisp.eval.Environment;
import jp.msfblue1.replindroid.lisp.eval.Evaluator;
import jp.msfblue1.replindroid.lisp.eval.SExpression;

import jp.msfblue1.replindroid.lisp.exception.EndOfFileException;
import jp.msfblue1.replindroid.lisp.exception.LispException;
import jp.msfblue1.replindroid.lisp.reader.Reader;

/**
 * Lispインタプリタ
 * 
 * @author tetsuya
 *
 */
public class Main {

	/**
	 * Lispインタプリタの対話的実行
	 * 
	 * @param args コマンドライン引数。与えられても無視される。
	 * @throws IOException 入出力エラー
	 */
	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Reader reader = new Reader(in);

		//env.define()で記述していた束縛系の実装はEnvironmentクラスに移動

		Environment globalEnv = Environment.createGlobalEnv();

		// REPL(Read-Eval-Print-Loop)
		try {
			while (true) {
				try {
					System.out.print("lisp> ");
					SExpression exp = reader.read();
					SExpression value = Evaluator.eval(exp, globalEnv);
					System.out.println(value);
				} catch (EndOfFileException e) {
					break;
				} catch (LispException e) {
					System.out.println("[Err Cause]> "+e.getMessage());
					for (StackTraceElement err: e.getStackTrace()) {
						System.out.println("[Err StackTrace]> "+err);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		in.close();
	}

}
