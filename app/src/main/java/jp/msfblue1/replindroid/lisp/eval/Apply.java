package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Apply implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env, SExpression argument) {
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException("");
        }
        SExpression proc = ((ConsCell) argument).getCar();
        SExpression applyArgs = ((ConsCell) argument).getCdr();

        if(!(applyArgs instanceof ConsCell)){
            throw new ArgumentException("");
        }

        List<SExpression> list = new LinkedList<>();

        List<SExpression> sexps = ConsCell.toList(applyArgs);
        Iterator<SExpression> itr = sexps.iterator();
        SExpression look;
        while (itr.hasNext()){
            look = itr.next();
            if(!itr.hasNext()){
                if (look instanceof EmptyList){
                    return Evaluator.eval(ConsCell.getInstance(proc,EmptyList.getInstance()),env);
                }
                if(!(look instanceof ConsCell)){
                    throw new ArgumentException("need ConsCell");
                }
                list.addAll(ConsCell.toList(look));
                break;
            }
            list.add(look);
        }

        return Evaluator.eval(ConsCell.getInstance(proc,ConsCell.toConsCell(list)),env);
    }

    @Override
    public String toString() {
        return "#<subr apply>";
    }
}
