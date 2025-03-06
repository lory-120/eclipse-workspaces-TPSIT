package model;

import java.util.concurrent.Semaphore;
import utilities.*;

/**
 * il n max di uova è 2000 (2000 permits)
 */

public class Magazzino { //è il buffer
	
	//attributi
	private static final int MAX_CAPACITY = 2000;
	private Semaphore occupazioneMagazzino;
	private Semaphore mutexMagazzino;
	
	
	//metodo cosruttore
	public Magazzino() {
		this.mutexMagazzino = new Semaphore(1);
		
	}
	
	
	//metodi della funzione
	
	//metodo richiamato dal produttore
	public void addUova(int nUovaAggiunte) throws InterruptedException, MagazzinoPienoException {
		occupazioneMagazzino.acquire(nUovaAggiunte);
	}
	
	
	public int buyUova(int nUovaDaComprare) throws InterruptedException, UovaNonSufficienteException {
		
	}


	//metodo toString
	

}
