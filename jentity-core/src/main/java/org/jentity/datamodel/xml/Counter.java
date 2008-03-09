package org.jentity.datamodel.xml;

public class Counter {
	private int counter = 0;
	
	public int getValue() {
		return counter;
	}
	public void setCounter(int value) {
		this.counter=value;
	}
	public void incrementCounter(int amount) {
		counter = counter + amount;
	}
	public void resetCounter() {
		counter = 0;
	}
}
