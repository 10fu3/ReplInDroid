package jp.msfblue1.replindroid.lisp.eval;

import jp.msfblue1.replindroid.lisp.exception.TypeException;

import java.util.LinkedList;

/**
 * Cons cell (ドット対)
 *
 * @author tetsuya
 *
 */
public class ConsCell implements SExpression {
	private SExpression car;
	private SExpression cdr;

	public SExpression getCar() {
		return this.car;
	}

	public SExpression getCdr() {
		return this.cdr;
	}

	private ConsCell(SExpression car, SExpression cdr) {
		this.car = car;
		this.cdr = cdr;
	}

	/**
	 * Cons Cell（ドット対）を構築する。
	 *
	 * @param car car部
	 * @param cdr cdr部
	 * @return 指定されたcar部とcdr部で構成されるCons Cell（ドット対）
	 */
	public static ConsCell getInstance(SExpression car, SExpression cdr) {
		return new ConsCell(car, cdr);
	}

	@Override
	public boolean isList() {
		return this.cdr.isList();
	}
	@Override
	public String toString() {
		if(this.car instanceof Symbol &&
				"quote".equalsIgnoreCase(((Symbol)this.car).getName()) &&
				this.cdr instanceof ConsCell &&
				((ConsCell)this.cdr).cdr instanceof EmptyList){
			return String.format("'%s",((ConsCell)this.cdr).car);
		}

		StringBuilder joinedString = new StringBuilder();
		joinedString.append("(");

		ConsCell lookCell = this;

		while (true){
			joinedString.append(lookCell.getCar());

			if(!(lookCell.getCdr() instanceof ConsCell)){
				if(!(lookCell.getCdr() instanceof EmptyList)){
					joinedString.append(" . ");
					joinedString.append(lookCell.cdr);
				}
				joinedString.append(")");
				break;
			}

			lookCell = (ConsCell) lookCell.getCdr();
			joinedString.append(" ");
		}
		return joinedString.toString();
	}

	public static java.util.List<SExpression> toList(SExpression cons){
		LinkedList<SExpression> list = new LinkedList<>();

		while (!(cons instanceof EmptyList)) {
			if (!(cons instanceof ConsCell)) {
				throw new TypeException("");
			}
			list.add(((ConsCell) cons).getCar());
			cons = ((ConsCell) cons).getCdr();
		}

		return list;
	}

	public static ConsCell toConsCell(java.util.List<SExpression> sexps){
		ConsCell head = ConsCell.getInstance(EmptyList.getInstance(),EmptyList.getInstance());

		ConsCell look = head;
		ConsCell beforeLook = null;

		for(SExpression sexp : sexps){
			look.car = sexp;
			look.cdr = ConsCell.getInstance(EmptyList.getInstance(),EmptyList.getInstance());
			beforeLook = look;
			look = (ConsCell) look.cdr;
		}
		if(beforeLook != null){
			beforeLook.cdr = EmptyList.getInstance();
		}
		return head;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ConsCell)){
			return false;
		}
		if(!this.car.equals(((ConsCell)obj).car)){
			return false;
		}
		return this.cdr.equals(((ConsCell)obj).cdr);
	}
}
