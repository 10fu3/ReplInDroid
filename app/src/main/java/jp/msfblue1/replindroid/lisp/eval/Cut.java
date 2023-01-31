package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.ArgumentException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Cut implements SpecialForm{
    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public SExpression apply(Environment env, SExpression argument) {
        if(!(argument instanceof ConsCell)){
            throw new ArgumentException("");
        }

        SExpression subrSymbol = ((ConsCell) argument).getCar();

        Iterator<SExpression> argsItr = ConsCell.toList(((ConsCell) argument).getCdr()).iterator();

        int count = 0;

        List<SExpression> cutValues = new LinkedList<>();
        List<SExpression> values = new LinkedList<>();


        SExpression arg;
        while (argsItr.hasNext()){
            count++;
            arg = argsItr.next();

            if(arg instanceof Symbol && "<>".equalsIgnoreCase(((Symbol)arg).getName())){
                cutValues.add(Symbol.getInstance("x"+count));
                values.add(Symbol.getInstance("x"+count));
                continue;
            }

            values.add(arg);
        }

        SExpression runSexp = ConsCell.getInstance(Symbol.getInstance("lambda"),
                ConsCell.getInstance(ConsCell.toConsCell(cutValues),
                        ConsCell.getInstance(
                                ConsCell.getInstance(
                                        subrSymbol,
                                        ConsCell.toConsCell(values)
                                ),
                                EmptyList.getInstance()
                        )
                )
        );

        return Evaluator.eval(runSexp,env);
    }

    @Override
    public String toString() {
        return "#<macro cut>";
    }
}
