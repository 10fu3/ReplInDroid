package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.TypeException;

/**
 * 整数値
 * 
 * @author tetsuya
 *
 */
public class Int implements Number {
	private Integer value;

	public Integer getValue() {
		return value;
	}

	private Int(Integer value) {
		this.value = value;
	}

	/**
	 * 整数値のインスタンスを得る。
	 * 
	 * @param value 整数値
	 * @return 整数値のインスタンス
	 */
	public static Int valueOf(int value) {
		return new Int(value);
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!((obj instanceof Int))){
			return false;
		}
		return this.value.equals(((Int) obj).getValue());
	}

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public Number add(Number number) {
		if(number instanceof Int){
			return Int.valueOf(this.value + ((Int) number).getValue());
		}
		if(number instanceof Float){
			return Float.valueOf(this.value + ((Float)number).getValue());
		}
		throw new TypeException("invalid type operation");
	}

	@Override
	public Number minus(Number number) {
		if(number instanceof Int){
			return Int.valueOf(this.value - ((Int) number).getValue());
		}
		if(number instanceof Float){
			return Float.valueOf(this.value - ((Float)number).getValue());
		}
		throw new TypeException("invalid type operation");
	}

	@Override
	public Number multiply(Number number) {
		if(number instanceof Int){
			return Int.valueOf(this.value * ((Int) number).getValue());
		}
		if(number instanceof Float){
			return Float.valueOf(this.value * ((Float)number).getValue());
		}
		throw new TypeException("invalid type operation");
	}

	@Override
	public Number divide(Number number) {
		if(number instanceof Int){
			return Int.valueOf(this.value / ((Int) number).getValue());
		}
		if(number instanceof Float){
			return Float.valueOf(this.value / ((Float)number).getValue());
		}
		throw new TypeException("invalid type operation");
	}

	@Override
	public Number mod(Number number) {
		if(number instanceof Int){
			return Int.valueOf(this.value % ((Int) number).getValue());
		}
		if(number instanceof Float){
			return Float.valueOf(this.value % ((Float)number).getValue());
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
			return this.value.compareTo(((Int) o).getValue());
		}
		if(o instanceof Float){
			return java.lang.Float.valueOf(this.value).compareTo(((Float) o).getValue());
		}
		return Integer.MAX_VALUE;
	}
}
