package model;

public class Pescatore extends Thread {
	
	private final static int PAUSE_TIME = 1000;
	private final static int MAX_WAIT_TIME = 200;
	private final static int MIN_WAIT_TIME = 800;
	private int fishingTime;
	private final int FISHER_ID;
	private Laghetto laghetto;
	
	public Pescatore(Laghetto laghetto, int fisherID) {
		//genera un tempo di pesca comrpeso tra minWaitTime e maxWaitTime
		this.fishingTime = (int) ((Math.random() * ((MAX_WAIT_TIME - MIN_WAIT_TIME) + 1)) + MIN_WAIT_TIME);
		this.laghetto = laghetto;
		this.FISHER_ID = fisherID;
	}
	
	
	/**
	 * il pescatore deve:
	 * - aspettare il tempo di pausa PAUSE_TIME
	 * - provare ad accedere al laghetto per pescare:
	 * 		- se ci riesce, pesca 1 pesce col tempo fishingTime
	 * 		- se non ci riesce, 
	 */
	public void run() {
		while(true) {
			try {
				
			}
		}
	}
	
}
