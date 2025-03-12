package model;

import java.util.concurrent.Semaphore;

public class Toilet {

	private Semaphore accessoToilet;
	
	public Toilet() {
		accessoToilet = new Semaphore(1);
	}
	
	/*
	public void usaToilet(int time) throws ToiletOccupatoException, InterruptedException {
		try {
			accessoToilet.tryAcquire();
			String name = Thread.currentThread().getName();
			System.out.println(name + " sta occupando il toilet.");
			Thread.sleep(time);
			System.out.println(name + " ha liberato il toilet. Ci sono " + accessoToilet.getQueueLength() + " in attesa per usarlo.");
		} finally {
			accessoToilet.release();
		}
	}
	*/
	
	
	public void usaToilet(int time) throws InterruptedException {
	    if(!accessoToilet.tryAcquire()) { //se non riesce ad occupare il toilet:
	        System.out.println(Thread.currentThread().getName() + //stampa un messaggio
	                          " è in attesa per il toilet. Ci sono " + 
	                          (accessoToilet.getQueueLength() + 1) + " persone in attesa.");
	        accessoToilet.acquire(); //mettiti in coda per occupare il toilet
	    }
	    
	    try {
	        String name = Thread.currentThread().getName(); //salva il nome della persona
	        System.out.println(name + " sta occupando il toilet.");
	        Thread.sleep(time); //simula l'uso del toilet
	        System.out.println(name + " ha liberato il toilet. Ci sono " + 
	                          accessoToilet.getQueueLength() + " in attesa per usarlo.");
	    } finally {
	        accessoToilet.release(); //rilascia il toilet a chi serve
	    }
	}
	
}
