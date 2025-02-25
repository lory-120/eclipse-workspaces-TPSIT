package model;

public class Addetto extends Thread {

	private static final int N_PESCI = 10;
	private static final int MIN_WAIT_TIME = 300;
	private static final int MAX_WAIT_TIME = 600;
	private int refillTime;
	
	
	public Addetto() {
		//genera un tempo di pesca comrpeso tra minWaitTime e maxWaitTime
		this.refillTime = (int) ((Math.random() * ((MAX_WAIT_TIME - MIN_WAIT_TIME) + 1)) + MIN_WAIT_TIME);
	}
	
}
