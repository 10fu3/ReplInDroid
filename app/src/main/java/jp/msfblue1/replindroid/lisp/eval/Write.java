package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

public class Write implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException("");
        }
        if(!(((ConsCell) argument).getCdr() instanceof EmptyList)){
            throw new TypeException("");
        }
        System.out.print(((ConsCell) argument).getCar());
        return Undef.getInstance();
    }
}
