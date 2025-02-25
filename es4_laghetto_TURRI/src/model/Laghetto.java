package model;

import java.util.concurrent.Semaphore;
import utilities.*;

public class Laghetto { //nel nostro caso, Laghetto è una risorsa

	private int nPesci;
	private int minPesci;
	private int maxPesci;
	private Semaphore accessoLaghetto;
	
	public Laghetto(int minPesci, int maxPesci) {
		this.minPesci = minPesci;
		this.maxPesci = maxPesci;
		this.nPesci = 0;
		this.accessoLaghetto = new Semaphore(1);
	}
	
	
	public void pesca(int fisherID, int fishingTime) throws InterruptedException, FishingDeniedException {
		accessoLaghetto.acquire();
		
		try { //sezione critica, si pesca dal laghetto
			if(nPesci <= minPesci) throw new FishingDeniedException("Ci sono troppi pochi pesci nel laghetto. (" + Integer.toString(nPesci) + ")");
			
			Thread.sleep(fishingTime); //simula il tempo di pesca
			nPesci--;
			System.out.println("Il pescatore " + Integer.toString(fisherID) + " ha pescato. Pesci rimanenti: " + Integer.toString(nPesci));
		} finally {
			accessoLaghetto.release();
		}
	}
	
	public void refill(int pesci, int refillerID, int refillTime) throws InterruptedException, RefillDeniedException {
		accessoLaghetto.acquire();
		
		try { //sezione critica, si riempie il laghetto
			//se ci sono troppi pesci lancia l'eccezione
			if(nPesci+pesci >= maxPesci) throw new RefillDeniedException("Ci sono troppi pesci nel laghetto. (" + Integer.toString(nPesci) + ")");
			
			Thread.sleep(refillTime); //simula il tempo di riempimento
			this.nPesci += pesci; //riempi di pesci
			System.out.println("L'addetto " + Integer.toString(refillerID) + " ha riempito il laghetto con " + Integer.toString(pesci) + " pesci. Pesci rimnenti: " + Integer.toString(nPesci));
		} finally {
			accessoLaghetto.release();
		}
	}
	
}
