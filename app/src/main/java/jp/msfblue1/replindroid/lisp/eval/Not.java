package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

public class Not implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException("");
        }
        ConsCell argCell = (ConsCell) argument;

        if(!(argCell.getCdr() instanceof EmptyList)){
            throw new ArgumentException("");
        }

        if(!(argCell.getCar() instanceof Bool)){
            throw new TypeException("");
        }

        return Bool.valueOf(!((Bool)argCell.getCar()).isValid());
    }
}
