package model;

import java.util.ArrayList;

public class Cabina {

	//attributi
	private static final long WAIT_TIME = 3000;
	private static final int TRAVEL_TIME = 2000;
	private final int MAX_SCIATORI;
	private final int MIN_SCIATORI;
	Sciatore[] sciatori;
	private int valleyQueue;
	private int mountainQueue;
	private boolean isAtValley;
	private boolean isAtMountain;
	
	//metodo coscos
	public Cabina(int MAX_SCIATORI, int MIN_SCIATORI) {
		this.MAX_SCIATORI = MAX_SCIATORI;
		this.MIN_SCIATORI = MIN_SCIATORI;
		this.sciatori = new Sciatore[MAX_SCIATORI];
		this.valleyQueue = 0;
		this.mountainQueue = 0;
		this.isAtValley = true;
		this.isAtMountain = false;
	}
	
	/**
	 * 
	 */
	
	
	public synchronized void goToMountain() {
		while(sciatori.length < MIN_SCIATORI) {
			System.out.println("Gli sciatori a valle sono "+valleyQueue+". Attesa di altri sciatori...");
			waitForSkiers();
		}
		
		isAtMountain = true;
		isAtValley = false;
		
		System.out.println("La cabina è partita. Arrivo alla montagna...");
		travel();
		System.out.println("La cabina è arrivata alla montagna.");
		
	}
	
	public synchronized void goToValley() {
		
	}


	private void waitForSkiers() {
		try {
			Thread.sleep(WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void travel() {
		try {
			Thread.sleep(TRAVEL_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isAtValley() {
		return isAtValley;
	}
	public boolean isAtMountain() {
		return isAtMountain;
	}
	
}
