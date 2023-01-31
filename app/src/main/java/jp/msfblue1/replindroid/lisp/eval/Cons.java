package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;

public class Cons implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment environment, SExpression sexp) {
        if(!(sexp instanceof ConsCell)) {
            throw new ArgumentException("wrong number of arguments for "+this+" (required 2, got 0)");
        }
        int size = ConsCell.toList(sexp).size();
        if(size != 2) {
            throw new ArgumentException("wrong number of arguments for "+this+" (required 2, got "+size+")");
        }
        SExpression car = ((ConsCell)sexp).getCar();
        SExpression cdr = ((ConsCell)((ConsCell)sexp).getCdr()).getCar();
        return ConsCell.getInstance(car, cdr);
    }

    @Override
    public String toString() {
        return "#<subr cons>";
    }
}
