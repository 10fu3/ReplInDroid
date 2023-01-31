package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

public class IsEq implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {

        if(!(argument instanceof ConsCell)){
            throw new TypeException("");
        }

        ConsCell argCell = (ConsCell) argument;

        if(!(argCell.getCdr() instanceof ConsCell)){
            throw new TypeException("");
        }

        SExpression first = argCell.getCar();

        if(!(argCell.getCdr() instanceof ConsCell)){
            throw new TypeException("");
        }

        ConsCell secondCell =  (ConsCell) argCell.getCdr();

        if(!((secondCell.getCdr()) instanceof EmptyList)){
            throw new ArgumentException("");
        }

        SExpression second = secondCell.getCar();

        if(first instanceof Atom){
            return Bool.valueOf(first.equals(second));
        }
        return Bool.valueOf(first == second);
    }
}
