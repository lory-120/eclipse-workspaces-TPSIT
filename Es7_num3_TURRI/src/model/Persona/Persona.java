package model.Persona;

import java.util.concurrent.ThreadLocalRandom;

import model.ToiletDoppioAccesso;

public class Persona extends Thread {

	private static final int MIN_WAIT_TIME = 300;
	private static final int MAX_WAIT_TIME = 450;
	private ToiletDoppioAccesso t;
	private boolean isDone;
	
	public Persona(String nome, ToiletDoppioAccesso t) {
		setName(nome);
		this.t = t;
		this.isDone = false;
	}
	
	
	@Override
	public void run() {
		while(!isDone) {
			try {
				t.usaToilet(this, getRandomSleepTime());
				isDone = true;
			} catch(InterruptedException e) {
				System.out.println("!!! " + e.getMessage());
			}
		}
	}
	
	
	private static int getRandomSleepTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME+1);
	}
	
}
