package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;

import java.util.List;

public class Assoc implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env, SExpression argument) {
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException("need ConsCell");
        }
        SExpression targetKey = ((ConsCell) argument).getCar();
        SExpression list = ((ConsCell) argument).getCdr();
        if(!(list instanceof ConsCell) || !(((ConsCell)list).getCar() instanceof ConsCell)){
            throw new ArgumentException("need ConsCell");
        }
        List<SExpression> map = ConsCell.toList(((ConsCell) list).getCar());

        for(SExpression item : map){
            if(!(item instanceof ConsCell)){
                throw new ArgumentException("need ConsCell");
            }
            if(((ConsCell) item).getCar().equals(targetKey)){
                return item;
            }
        }
        return Bool.valueOf(false);
    }

    @Override
    public String toString() {
        return "#<subr assoc>";
    }
}
