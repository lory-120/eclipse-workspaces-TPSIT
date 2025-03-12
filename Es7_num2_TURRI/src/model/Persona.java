package model;

import java.util.concurrent.ThreadLocalRandom;

public class Persona extends Thread {

	private static final int MIN_SLEEP_TIME = 100;
	private static final int MAX_SLEEP_TIME = 450;
	private Toilet toilet;
	private boolean isDoneUsing;
	
	public Persona(Toilet toilet, String name) {
		this.toilet = toilet;
		this.isDoneUsing = false;
		setName(name);
	}
	
	@Override
	public void run() {
		while(!this.isDoneUsing) {
			try {
				toilet.usaToilet(getRandomSleepTime());
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			} finally {
				this.isDoneUsing = true;
			}
		}
	}
	
	private static int getRandomSleepTime() {
		return ThreadLocalRandom.current().nextInt(MIN_SLEEP_TIME, MAX_SLEEP_TIME+1);
	}
	
}
