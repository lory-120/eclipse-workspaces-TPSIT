package model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cassa {

	private Lock lock = new ReentrantLock();
	private String nome;
	
	public Cassa(String nome) {
		this.nome = nome;
	}
	
	public void acquistaBiglietto(Cliente c, int time) {
		lock.lock();
		try {
			int bigliettiAcquistati = (c.isIscritto()) ? 3 : 1;
			
			Thread.sleep(time);
			
			System.out.print(c.getName()+" ha acquistato "+bigliettiAcquistati+" biglietto/i. ");
			System.out.println(c.isIscritto() ? " Il cliente è iscritto al fan club." : "");
		} catch(InterruptedException e) {
			System.out.println("Errore nel monitor "+this.nome+": "+e.getMessage());
		} finally {
			lock.unlock();
		}
	}

}
