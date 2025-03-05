package model;

import java.util.concurrent.Semaphore;

public class Inscatolamento extends Thread {

	//attributi
	private final int BOXING_TIME;
	private final int BOX_CAPACITY; //la capacità di ogni scatola (N)
	private Semaphore accessoInscatolmento;
	
	//metodo costruttore
	public Inscatolamento(int BOXING_TIME, int BOX_CAPACITY) {
		this.BOXING_TIME = BOXING_TIME;
		this.BOX_CAPACITY = BOX_CAPACITY;
		this.accessoInscatolmento = new Semaphore(1, true); //true per il fairness
	}
	
	
	//metodi della funzione
	public void run() {
		accessoInscatolmento.tryAcquire();
		//(non so come continuare)
	}
	
	
}
