package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

public class Pipe implements SpecialForm{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env, SExpression argument) {
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException("");
        }

        ConsCell argCell = (ConsCell) argument;

        if(argCell.getCar() instanceof EmptyList){
            throw new ArgumentException("");
        }

        if(argCell.getCdr() instanceof EmptyList){
            throw new ArgumentException("");
        }

        SExpression baseValue = Evaluator.eval(argCell.getCar(),env);

        java.util.List<SExpression> applyList = ConsCell.toList(argCell.getCdr());

        ConsCell arg;

        for(SExpression applyElement : applyList){
            if(!(applyElement instanceof ConsCell)){
                throw new TypeException("");
            }
            arg = (ConsCell) applyElement;
            if(!(arg.getCar() instanceof Symbol)){
                throw new TypeException("");
            }
            arg = ConsCell.getInstance(arg.getCar(),ConsCell.getInstance(baseValue,arg.getCdr()));
            baseValue = Evaluator.eval(arg,env);
        }
        return baseValue;
    }
}
