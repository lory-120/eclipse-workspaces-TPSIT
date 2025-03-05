package model;

import java.util.concurrent.Semaphore;
import utilities.*;

public class Assemblaggio extends Thread {

	//atributi
	private final int ASSEMBLY_TIME;
	private final int MAX_CHIPS;
	private int nProcessoriProdotti;
	private Semaphore accessoProduzione;
	private Inscatolamento repartoInscatolamento;
	
	
	//metodo costruttore
	public Assemblaggio(int MAX_CHIPS, int ASSEMBLY_TIME, Inscatolamento inscatolamento) {
		this.MAX_CHIPS = MAX_CHIPS;
		this.ASSEMBLY_TIME = ASSEMBLY_TIME;
		this.repartoInscatolamento = inscatolamento;
		this.accessoProduzione = new Semaphore(MAX_CHIPS, true); //true per il fairness
	}
	
	
	//metodi della funzione
	
	//acquisisci il permit, testa se ci sono troppi processori, produci e metti in stock
	public void run() {
		while(true) {
			if(nProcessoriProdotti >= MAX_CHIPS) throw new AssemblyDeniedException("Non si possono produrre altri microprocessori. Prodotto da inscatolare: " + Integer.toString(nProcessoriProdotti) + ".");
			accessoProduzione.tryAcquire(1);
			nProcessoriProdotti++;
			//ora si vede se su può inscatolare
			if(nProcessoriProdotti >= repartoInscatolamento.get)
			try {
				Thread.sleep(ASSEMBLY_TIME);
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	
}
