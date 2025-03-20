package model;

import java.util.concurrent.ThreadLocalRandom;

public class Auto extends Thread {

	private int parkingTime;
	private Parcheggio p;
	
	public Auto(Parcheggio p, String name) {
		this.p = p;
		this.parkingTime = getRandomParkingTime();
		setName(name);
	}
	
	@Override
	public void run() {
		try {
			p.park();
			Thread.sleep(parkingTime);
			p.leave();
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private int getRandomParkingTime() {
		return ThreadLocalRandom.current().nextInt(400, 900+1);
	}
	
}
