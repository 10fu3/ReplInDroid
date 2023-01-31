package jp.msfblue1.replindroid.lisp.eval;


import java.util.Iterator;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

public class Add implements Subroutine{

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {
        if(argument instanceof EmptyList){
            return Int.valueOf(0);
        }
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException(argument.toString());
        }
        Iterator<SExpression> itr =ConsCell.toList(argument).iterator();
        Number result = (Number)  itr.next();
        SExpression next;
        while (itr.hasNext()){
            next = itr.next();
            if(!(next instanceof Number)){
                throw new TypeException(next.toString());
            }
            result = result.add((Number)next);
        }
        return result;
    }

    @Override
    public String toString(){
        return "#<subr +>";
    }
}
