package model;

import java.util.concurrent.Semaphore;

import model.exception.QuantitaException;

public class MercatoOrtofrutticolo {
	private final int QUANTITA_MAX;
	private int quantitaCiliegie;
	private final double PREZZO_ACQUISTO;
	private final double PREZZO_VENDITA;
	private Semaphore spazioDisponibile; //gestito dal produttore
	private Semaphore merceDisponibile; 
	
	
	public MercatoOrtofrutticolo(int max, double PA, double PV, Semaphore spazioDisponibile, Semaphore merceDisponibile) {
		this.QUANTITA_MAX=max;
		this.PREZZO_ACQUISTO=PA;
		this.PREZZO_VENDITA=PV;
		this.quantitaCiliegie=0;
		this.spazioDisponibile=spazioDisponibile;
		this.merceDisponibile=merceDisponibile;
	}
	
	
	public int getQuantitaCiliegie() {
		return quantitaCiliegie;
	}


	public void setQuantitaCiliegie(int quantitaCiliegie) {
		this.quantitaCiliegie = quantitaCiliegie;
	}


	public Semaphore getSpazioDisponibile() {
		return spazioDisponibile;
	}


	public void setSpazioDisponibile(Semaphore spazioDisponibile) {
		this.spazioDisponibile = spazioDisponibile;
	}


	public Semaphore getMerceDisponibile() {
		return merceDisponibile;
	}


	public void setMerceDisponibile(Semaphore merceDisponibile) {
		this.merceDisponibile = merceDisponibile;
	}


	public int getQUANTITA_MAX() {
		return QUANTITA_MAX;
	}


	public double getPREZZO_ACQUISTO() {
		return PREZZO_ACQUISTO;
	}


	public double getPREZZO_VENDITA() {
		return PREZZO_VENDITA;
	}


	public double vendi(int quantita) throws InterruptedException, QuantitaException { //produttore 
	
		if(!spazioDisponibile.tryAcquire(quantita)) {
			throw new QuantitaException("Raggiunto limite max");
		}
		quantitaCiliegie+=quantita;
		System.out.println(Thread.currentThread().getName()+" ha venduto "+quantita+" q. Quantità attuale: "+quantitaCiliegie);
		merceDisponibile.release(quantita);
		return quantita*PREZZO_VENDITA;
	}
	
	public double acquista(int quantita) throws InterruptedException, QuantitaException { //grossista
	
		if(! merceDisponibile.tryAcquire(quantita)) {
			throw new QuantitaException("Quantità non sufficiente");
		}
		quantitaCiliegie-=quantita;
		System.out.println(Thread.currentThread().getName()+" ha acquistato "+quantita+" q. Quantità attuale: "+quantitaCiliegie);
		spazioDisponibile.release(quantita);
		return quantita*PREZZO_ACQUISTO;
	}
}
