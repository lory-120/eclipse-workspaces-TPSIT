package model;

import java.util.concurrent.Semaphore;

public class Laghetto { //nel nostro caso, Laghetto è una risorsa

	private int nPesci;
	private int minPesci;
	private int maxPesci;
	private Semaphore accessoLaghetto;
	
	public Laghetto(int minPesci, int maxPesci) {
		this.minPesci = minPesci;
		this.maxPesci = maxPesci;
		this.accessoLaghetto = new Semaphore(1);
	}
	
	
	public void pesca() throws InterruptedException {
		accessoLaghetto.acquire();
		//sezione critica, si pesca dal laghetto
		try {
			if(nPesci < minPesci) throw new FishingDeniedException("Ci sono troppi pochi pesci nel laghetto.");
		}
	}
	
}
