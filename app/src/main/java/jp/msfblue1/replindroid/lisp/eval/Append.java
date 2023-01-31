package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;

import java.util.Iterator;
import java.util.List;

public class Append implements Subroutine{

    private SExpression append(SExpression left, SExpression right) {
        if(left instanceof EmptyList) {
            return right;
        }
        if(!(left instanceof ConsCell)) {
            throw new ArgumentException("list required, but got "+left);
        }
        return ConsCell.getInstance(((ConsCell)left).getCar(), append(((ConsCell)left).getCdr(), right));
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {
        if(!(argument instanceof ConsCell)) {
            return argument;
        }
        List<SExpression> args = ConsCell.toList(argument);
        if(args.size() == 1) {
            return argument;
        }

        Iterator<SExpression> itr = args.iterator();
        SExpression list = EmptyList.getInstance();
        if(itr.hasNext()){
            list = itr.next();
        }
        while (itr.hasNext()){
            list = append(list,itr.next());
        }

        return list;
    }
}
