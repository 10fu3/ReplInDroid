package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.Iterator;

public class OrLess implements Subroutine {
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {
        java.util.List<SExpression> list = ConsCell.toList(argument);
        if(list.size() <= 1){
            throw new ArgumentException("");
        }

        Iterator<SExpression> itr = list.iterator();

        SExpression compareTo = itr.next();
        SExpression compareFrom;

        while(itr.hasNext()){
            compareFrom = itr.next();

            if(!(compareFrom instanceof Number) || !(compareTo instanceof Number)){
                throw new TypeException(">= argument required type is Number, but got other");
            }
            int result = ((Number) compareFrom).compareTo((Number) compareTo);
            if(result > 0){
                return Bool.valueOf(false);
            }
            compareTo = compareFrom;
        }
        return Bool.valueOf(true);
    }
}
