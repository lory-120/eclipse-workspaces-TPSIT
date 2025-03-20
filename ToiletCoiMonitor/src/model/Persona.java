package model;

import java.util.concurrent.ThreadLocalRandom;

public class Persona extends Thread {

	private int waitTime;
	private Toilet t;
	
	public Persona(Toilet t, String name) {
		this.t = t;
		this.waitTime = getRandomWaitTime();
		setName(name);
	}
	
	
	@Override
	public void run() {
		t.usaBagno();
		try {
			Thread.sleep(waitTime);
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
		t.liberaBagno();
	}
	
	
	private int getRandomWaitTime() {
		return ThreadLocalRandom.current().nextInt(400, 1000+1);
	}
	
}
