package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.TypeException;

public class Float implements Number{

    private final java.lang.Float value;

    private Float(float value){
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public float getValue(){
        return this.value;
    }

    public static Float valueOf(float value){
        return new Float(value);
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(!((obj instanceof Float))){
            return false;
        }
        return this.value == ((Float) obj).getValue();
    }

    @Override
    public Number add(Number number) {
        if(number instanceof Int){
            return Float.valueOf(this.value + ((Int)number).getValue());
        }
        if(number instanceof Float){
            return Float.valueOf(this.value + ((Float)number).getValue());
        }
        throw new TypeException("invalid type operation");
    }

    @Override
    public Number minus(Number number) {
        if(number instanceof Int){
            return Float.valueOf(this.value - ((Int)number).getValue());
        }
        if(number instanceof Float){
            return Float.valueOf(this.value - ((Float)number).getValue());
        }
        throw new TypeException("invalid type operation");
    }

    @Override
    public Number multiply(Number number) {
        if(number instanceof Int){
            return Float.valueOf(this.value * ((Int)number).getValue());
        }
        if(number instanceof Float){
            return Float.valueOf(this.value * ((Float)number).getValue());
        }
        throw new TypeException("invalid type operation");
    }

    @Override
    public Number divide(Number number) {
        if(number instanceof Int){
            return Float.valueOf(this.value / ((Int)number).getValue());
        }
        if(number instanceof Float){
            return Float.valueOf(this.value / ((Float)number).getValue());
        }
        throw new TypeException("invalid type operation");
    }

    @Override
    public Number mod(Number number) {
        if(number instanceof Int){
            return Float.valueOf(this.value % ((Int) number).getValue());
        }
        if(number instanceof Float){
            return Float.valueOf(this.value + ((Float)number).getValue());
        }
        throw new TypeException("invalid type operation");
    }

    @Override
    public Number sin() {
        double value = Double.parseDouble(this.value.toString());
        float result = java.lang.Float.parseFloat(((Double) Math.sin(value)).toString());
        return Float.valueOf(result);
    }

    @Override
    public Number cos() {
        double value = Double.parseDouble(this.value.toString());
        float result = java.lang.Float.parseFloat(((Double) Math.cos(value)).toString());
        return Float.valueOf(result);
    }

    @Override
    public Number tan() {
        double value = Double.parseDouble(this.value.toString());
        float result = java.lang.Float.parseFloat(((Double) Math.tan(value)).toString());
        return Float.valueOf(result);
    }

    @Override
    public int compareTo(Number o) {
        if(o instanceof Int){
            return this.value.compareTo(java.lang.Float.parseFloat(String.valueOf(((Int) o).getValue())));
        }
        if(o instanceof Float){
            return this.value.compareTo(((Float) o).getValue());
        }
        return Integer.MAX_VALUE;
    }
}
