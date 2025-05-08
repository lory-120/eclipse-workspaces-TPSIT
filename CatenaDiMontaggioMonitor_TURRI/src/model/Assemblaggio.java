package model;

import java.util.concurrent.ThreadLocalRandom;

public class Assemblaggio extends Thread {
	
	private static final int MIN_WAIT_TIME = 1500;
	private static final int MAX_WAIT_TIME = 3500;
	private final GestioneCatenaMontaggio catenaMontaggio;
	
	public Assemblaggio(GestioneCatenaMontaggio catenaMontaggio) {
		this.catenaMontaggio = catenaMontaggio;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				catenaMontaggio.assembla();
				Thread.sleep(getWaitTime());
			} catch(InterruptedException e) {
				System.out.println("Assemblaggio interrotto: " + e.getMessage());
			}
		}
	}
	
	private static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME);
	}
	
}
