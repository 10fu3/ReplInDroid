package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.Iterator;
import java.util.List;

public class If implements SpecialForm {

    @Override
    public SExpression apply(Environment env, SExpression argument) {

        try{

            List<SExpression> args = ConsCell.toList(argument);

            if(args.size() <= 1 || args.size() >= 4){
                throw new ArgumentException("too many argument");
            }

            Iterator<SExpression> itr = args.iterator();

            SExpression statement = itr.next();
            SExpression onTrue = itr.next();

            if(args.size() == 3){
                SExpression onFalse = itr.next();
                if(Evaluator.eval(statement,env).equals(Bool.valueOf(false))){
                    return Evaluator.eval(onFalse,env);
                }
                return Evaluator.eval(onTrue,env);
            }
            if(args.size() == 2){
                if(Evaluator.eval(statement,env).equals(Bool.valueOf(false))){
                    return Undef.getInstance();
                }
                return Evaluator.eval(onTrue,env);
            }

        }catch (ClassCastException e){
        }
        throw new TypeException("need ConsCell");
    }

    @Override
    public String toString() {
        return "#<syntax if>";
    }

    @Override
    public boolean isList() {
        return false;
    }
}