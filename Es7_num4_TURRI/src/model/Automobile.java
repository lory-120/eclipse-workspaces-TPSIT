package model;

import java.util.concurrent.ThreadLocalRandom;

public class Automobile extends Thread {

	private static final int MIN_WAIT_TIME = 1000;
	private static final int MAX_WAIT_TIME = 2500;
	private Parcheggio p;
	private boolean isDone;
	
	public Automobile(String nome, Parcheggio p) {
		setName(nome);
		this.p = p;
		this.isDone = false;
	}
	
	@Override
	public void run() {
		while(!isDone) {
			try {
				p.sosta(getRandomParkingTime());
				isDone = true;
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private static int getRandomParkingTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME+1);
	}
	
}
