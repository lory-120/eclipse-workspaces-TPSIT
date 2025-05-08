package model;

import java.util.concurrent.ThreadLocalRandom;

public class Inscatolamento extends Thread {

	private static final int MIN_WAIT_TIME = 2000;
	private static final int MAX_WAIT_TIME = 4000;
	private final GestioneCatenaMontaggio catenaMontaggio;
	
	public Inscatolamento(GestioneCatenaMontaggio catenaMontaggio) {
		this.catenaMontaggio = catenaMontaggio;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				catenaMontaggio.inscatola();
				Thread.sleep(getWaitTime());
			} catch(InterruptedException e) {
				System.out.println("Inscatolamento interrotto: " + e.getMessage());
			}
		}
	}
	
	private static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME);
	}
	
}
