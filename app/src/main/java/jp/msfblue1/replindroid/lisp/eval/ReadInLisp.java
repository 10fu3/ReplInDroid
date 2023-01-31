package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.EndOfFileException;
import jp.msfblue1.replindroid.lisp.exception.LispException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;
import jp.msfblue1.replindroid.lisp.reader.Reader;

import java.io.*;

public class ReadInLisp implements SpecialForm{

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env, SExpression argument) {
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException("");
        }

        SExpression filePath = ((ConsCell) argument).getCar();

        if(!(filePath instanceof Str)){
            throw new TypeException("");
        }

        File file = new File(((Str)filePath).getRawValue().toString());

        try{

            if (!file.exists()) {
                throw new ArgumentException("not found: "+((Str) filePath).getRawValue().toString());
            }

            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            Reader reader = new Reader(in);

            while (true) {
                try {
                    SExpression exp = reader.read();
                    Evaluator.eval(exp, env);
                } catch (EndOfFileException e) {
                    break;
                } catch (LispException e) {
                    throw e;
                }
            }
            return Undef.getInstance();

        }catch (IOException e){

        }
        throw new TypeException("need type ConsCell");
    }

    @Override
    public String toString() {
        return "";
    }
}
