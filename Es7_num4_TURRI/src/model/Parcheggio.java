package model;

import java.util.concurrent.Semaphore;

public class Parcheggio {

	private Semaphore disponibilitaParcheggio;
	
	public Parcheggio(int nPosti) {
		this.disponibilitaParcheggio = new Semaphore(nPosti);
	}
	
	protected void sosta(int parkingTime) throws InterruptedException {
		String name = Thread.currentThread().getName();
		
		if(!disponibilitaParcheggio.tryAcquire(1)) {
			System.out.println("L'auto " + name + " ha provato ad accedere al parcheggio, ma è pieno. "
					+ "Ci sono altre " + disponibilitaParcheggio.getQueueLength() + " auto ad aspettare.");
			disponibilitaParcheggio.acquire(1);
		}
		
		try {
			System.out.println("L'auto " + name + " ha iniziato a sostare.");
			Thread.sleep(parkingTime);
			System.out.println("L'auto " + name + " ha liberato un posto nel parcheggio.");
		} finally {
			disponibilitaParcheggio.release(1);
		}
	}
	
}
