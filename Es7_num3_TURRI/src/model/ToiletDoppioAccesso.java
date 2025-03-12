package model;

import java.util.concurrent.Semaphore;

import model.Persona.Uomo;
import model.Persona.Donna;
import model.Persona.Persona;

public class ToiletDoppioAccesso {

	private Semaphore accessoToiletUomo;
	private Semaphore accessoToiletDonna;
	
	public ToiletDoppioAccesso() {
		this.accessoToiletUomo = new Semaphore(1);
		this.accessoToiletDonna = new Semaphore(1);
	}
	
	
	public void usaToilet(Persona p, int time) throws InterruptedException {
		if(p instanceof Uomo) {
			usaToiletUomo((Uomo)p, time);
		} else {
			usaToiletDonna((Donna)p, time);
		}
	}
	
	
	private void usaToiletUomo(Uomo p, int time) throws InterruptedException {
		String name = p.getName();
		
		if(!accessoToiletUomo.tryAcquire()) {
			System.out.println("L'uomo " + name + " si è messo in fila per il Toilet. " +
								"Uomini in fila per il Toilet Uomo: " + (accessoToiletUomo.getQueueLength()+1));
			accessoToiletUomo.acquire();
		}
		
		try {
			System.out.println("L'uomo " + name + " sta occupando il toilet.");
			Thread.sleep(time);
			System.out.println("L'uomo " + name + " ha liberato il toilet. Ci sono " + 
								accessoToiletUomo.getQueueLength() + " uomini in fila per il Toilet Uomo.");
		} finally {
			accessoToiletUomo.release();
		}
	}
	
	private void usaToiletDonna(Donna p, int time) throws InterruptedException {
		String name = p.getName();
		
		if(!accessoToiletDonna.tryAcquire()) {
			System.out.println("La donna " + name + " si è messa in fila per il Toilet. " +
								"Donne in fila per il Toilet Donna: " + (accessoToiletDonna.getQueueLength()+1));
			accessoToiletDonna.acquire();
		}
		
		try {
			System.out.println("La donna " + name + " sta occupando il toilet.");
			Thread.sleep(time);
			System.out.println("La donna " + name + " ha liberato il toilet. Ci sono " + 
								accessoToiletDonna.getQueueLength() + " donne in fila per il Toilet Donna.");
		} finally {
			accessoToiletDonna.release();
		}
	}
	
	
}
