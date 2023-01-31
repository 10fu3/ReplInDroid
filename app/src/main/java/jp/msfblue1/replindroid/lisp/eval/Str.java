package jp.msfblue1.replindroid.lisp.eval;

public class Str implements SExpression {

    private StringBuilder value;

    @Override
    public boolean isList() {
        return false;
    }

    public StringBuilder getRawValue(){
        return this.value;
    }

    public Str(StringBuilder sb){
        this.value = sb;
    }

    public static Str valueOf(StringBuilder sb){
        return new Str(sb);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.value.toString().equals(((Str)o).value.toString());
    }

    @Override
    public String toString() {
        return String.format("\"%s\"",this.value.toString());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
