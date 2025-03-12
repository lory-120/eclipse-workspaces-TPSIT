package model;

import java.util.concurrent.Semaphore;

import utilities.AcquistoNonPermessoException;
import utilities.VenditaNonPermessaException;

public class MercatoUova {

	private final int MAX_CAPACITY;
	private Semaphore accessoMercato;
	private int nUova;
	
	
	public MercatoUova(int MAX_CAPACITY) {
		this.MAX_CAPACITY = MAX_CAPACITY;
		this.accessoMercato = new Semaphore(1, true);
		this.nUova = 0;
	}
	
	
	public void vendi(int quantitaVenduta, int time) throws VenditaNonPermessaException, InterruptedException {
		String name = Thread.currentThread().getName();
		try {
			accessoMercato.acquire(); //inizio della sezione critica
			if(nUova+quantitaVenduta > MAX_CAPACITY) throw new VenditaNonPermessaException("Il mercato non può prendere altre " + quantitaVenduta + " uova. Ce ne sono già " + nUova + ".");
			Thread.sleep(time);
			nUova += quantitaVenduta;
			System.out.println("Il venditore " + name + " ha venduto " + quantitaVenduta + " uova al mercato. Capacità occupata: " + nUova + ".");
		} finally { //per rilasciare la risorsa, anche se c'è un imprevisto (es. una qualsiasi Exception)
			accessoMercato.release(); //fine della sezione critica
		}
		
	}
	
	
	public void acquista(int quantitaAcquistata, int time) throws AcquistoNonPermessoException, InterruptedException {
		String name = Thread.currentThread().getName();		
		try {
			accessoMercato.acquire(); //inizio della sezione critica
			if(nUova-quantitaAcquistata < 0) throw new AcquistoNonPermessoException("Il mercato non può vendere " + quantitaAcquistata + " uova al grossista " + name + ". Ce ne sono solo " + nUova + ".");
			Thread.sleep(time);
			nUova -= quantitaAcquistata;
			System.out.println("Il grossista " + name + " ha acquistato " + quantitaAcquistata + " uova. Rimanenti: " + nUova + ".");
		} finally {
			accessoMercato.release(); //fine della sezione critica
		}
		
	}
	
}
