package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Map implements Subroutine{
    @Override
    public boolean isList() {
        return false;
    }

    private List<ConsCell> convertList(List<List<SExpression>> se){
        int minLength = se.stream().mapToInt(List::size).min().getAsInt();
        List<SExpression>[] result = new List[minLength];
        for (int i = 0; i < minLength; i++) {
            result[i] = new ArrayList<>();
        }
        for (int i = 0; i < minLength; i++) {
            for(List<SExpression> childArgs : se){
                result[i].add(childArgs.get(i));
            }
        }
        return Stream.of(result).map(ConsCell::toConsCell).collect(Collectors.toList());
    }

    private SExpression eval(Environment env,Callable sub,List<ConsCell> se, int readiedIndex){
        if(se.size() == readiedIndex){
            return EmptyList.getInstance();
        }
        return ConsCell.getInstance(sub.apply(env,se.get(readiedIndex)),eval(env,sub,se,readiedIndex+1));
    }

    @Override
    public SExpression apply(Environment env,SExpression argument) {

        if(!(argument instanceof ConsCell)){
            throw new TypeException("");
        }

        ConsCell argCell = (ConsCell) argument;

        if(!(argCell.getCar() instanceof Callable)){
            throw new TypeException("");
        }

        List<List<SExpression>> args =
                ConsCell.toList(argCell.getCdr())
                .stream()
                .map(ConsCell::toList)
                .collect(Collectors.toList());

        return eval(env,(Callable) argCell.getCar(),convertList(args),0);
    }

    @Override
    public String toString() {
        return "#<subr map>";
    }
}
