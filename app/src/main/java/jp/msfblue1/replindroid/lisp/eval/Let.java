package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;
import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Let implements SpecialForm{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env, SExpression argument) {
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException("need ConsCell");
        }

        try{
            List<SExpression> args = ConsCell.toList(argument);

            if(args.size() != 2){
                throw new ArgumentException("argument size must be 2");
            }

            Iterator<SExpression> itr = args.iterator();
            SExpression binds = itr.next();
            SExpression body = itr.next();

            if(!(binds instanceof ConsCell)){
                throw new TypeException("bind argument must be ConsCell");
            }

            List<SExpression> bindArgs = ConsCell.toList(binds);

            List<Iterator<SExpression>> params = bindArgs.stream().map(bindArg -> {
                if (!(bindArg instanceof ConsCell)) {
                    throw new TypeException("bind argument must be ConsCell");
                }
                List<SExpression> bindParams = ConsCell.toList(bindArg);
                if (bindParams.size() != 2) {
                    throw new TypeException("bind argument size must be 2");
                }
                return bindParams.iterator();
            }).collect(Collectors.toList());

            List<SExpression> symbols = new LinkedList<>();
            List<SExpression> initValues = new LinkedList<>();

            for(Iterator<SExpression> param : params){
                SExpression symbol = param.next();
                if(!(symbol instanceof Symbol)){
                    throw new TypeException("need Symbol, but got -> "+symbol.toString());
                }
                symbols.add(symbol);
                SExpression initValue = Evaluator.eval(param.next(),env);
                initValues.add(initValue);
            }

            return new Closure(ConsCell.toConsCell(symbols),body,env)
                        .apply(env,ConsCell.toConsCell(initValues));


        }catch (ClassCastException e){
            e.printStackTrace();
        }
        throw new ArgumentException("need ConsCell");
    }

    @Override
    public String toString() {
        return "#<syntax let>";
    }
}
