package model;

import utilities.RefillDeniedException;

public class Addetto extends Thread {

	private static final int PAUSE_TIME = 3000;
	private static final int N_PESCI = 10;
	private static final int MIN_WAIT_TIME = 300;
	private static final int MAX_WAIT_TIME = 600;
	private final int REFILLER_ID;
	private final int REFILL_TIME;
	private Laghetto laghetto;
	
	
	public Addetto(Laghetto laghetto, int refillerID) {
		//genera un tempo di pesca comrpeso tra minWaitTime e maxWaitTime
		this.REFILL_TIME = (int) ((Math.random() * ((MAX_WAIT_TIME - MIN_WAIT_TIME) + 1)) + MIN_WAIT_TIME);
		this.REFILLER_ID = refillerID;
		this.laghetto = laghetto;
	}
	
	
	/**
	 * 
	 */
	@Override
	public void run() {
		while(true) {
			try {
				laghetto.refill(N_PESCI, REFILLER_ID, REFILL_TIME);
				Thread.sleep(PAUSE_TIME);
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			} catch(RefillDeniedException e) {
				System.out.println(e.getMessage());
				try {
					Thread.sleep(PAUSE_TIME);
				} catch(InterruptedException e1) {
					System.out.println(e.getMessage());
				}
			}
			
			
		}
	}
	
}
