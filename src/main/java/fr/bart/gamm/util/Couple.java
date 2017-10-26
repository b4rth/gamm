package fr.bart.gamm.util;

public class Couple<T1, T2> {

	private T1 element1;
	private T2 element2;
	
	public Couple() {
		
	}
	
	public Couple(T1 element1, T2 element2) {
		super();
		this.element1 = element1;
		this.element2 = element2;
	}

	public T1 getElement1() {
		return element1;
	}

	public void setElement1(T1 element1) {
		this.element1 = element1;
	}

	public T2 getElement2() {
		return element2;
	}

	public void setElement2(T2 element2) {
		this.element2 = element2;
	}
	
	
	
	
}
