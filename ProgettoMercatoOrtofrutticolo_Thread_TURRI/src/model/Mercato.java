package model;

import java.util.concurrent.Semaphore;

import utilities.MercatoPienoException;
import utilities.MercatoVuotoException;

public class Mercato {

	//attributi
	//capacità massima del mercato
	private final int MAX_CAPACITY;
	private final int P_VENDITA;
	private final int P_ACQUISTO;
	private Semaphore accessoMercato;
	private int currentStockQuantity;
	
	//metodo costruttore
	public Mercato(int MAX_CAPACITY, int P_VENDITA, int P_ACQUISTO) {
		this.MAX_CAPACITY = MAX_CAPACITY;
		
		if(P_VENDITA < P_ACQUISTO) {
			throw new IllegalArgumentException("Come fa il mercato a guadagnare, se compri a " + Integer.toString(P_ACQUISTO) + " e vendi a " + Integer.toString(P_VENDITA) + "!?!?!?");
		}
		
		this.P_VENDITA = P_VENDITA;
		this.P_ACQUISTO = P_ACQUISTO;
		this.currentStockQuantity = 0;
		this.accessoMercato = new Semaphore(1);
	}
	
	
	//metodi della funzione
	
	//qui il produttore vende al mercato (se non è pieno) per qp*P_VENDITA
	//qp è la quantità che il produttore vende al mercato
	public void vendi(int qp, int waitTime, int produttoreID) throws MercatoPienoException, InterruptedException {
		accessoMercato.acquire(); //entra nella sezione critica
		
		try { //sezione critica: il produttore vende al mercato, e il mercato si riempie
			if(this.currentStockQuantity + qp >= MAX_CAPACITY) throw new MercatoPienoException("Il produttore " + Integer.toString(produttoreID) + " non può aggiungere " + Integer.toString(qp) + " quintali. Ci sono " + Integer.toString(currentStockQuantity) + " quintali nel mercato.");
			int spesa = P_ACQUISTO * qp;
			this.currentStockQuantity += qp;
			Thread.sleep(waitTime);
			System.out.println("Il mercato ha comprato " + Integer.toString(qp) + " quintali di prodotto dal produttore " + Integer.toString(produttoreID) + ", spendendo " + Integer.toString(spesa) + ". Stock: " + Integer.toString(currentStockQuantity));
		} finally {
			accessoMercato.release(); //esce dalla sezione critica
		}
	}
	
	//qui il grossista compra dal mercato (se non è vuoto) per qg*P_ACQUISTO
	//qg è la quantità che il grossista compra dal mercato
	public void acquista(int qg, int waitTime, int grossistaID) throws MercatoVuotoException, InterruptedException {
		accessoMercato.acquire(); //entra nella sezione critica
		
		try { //sezione critica: il grossista compra dal mercato, ed esso si svuota
			if(this.currentStockQuantity - qg < 0) throw new MercatoVuotoException("Il grossista " + Integer.toString(grossistaID) + " ha richiesto " + Integer.toString(qg) + " quintali, ma ci sono solo " + Integer.toString(currentStockQuantity) + " quintali nel magazzino. ");
			int guadagno = P_VENDITA * qg;
			this.currentStockQuantity -= qg;
			Thread.sleep(waitTime);
			System.out.println("Il mercato ha venduto " + Integer.toString(qg) + " quintali al grossista " + Integer.toString(grossistaID) + ", guadagnando " + Integer.toString(guadagno) + ". Stock: " + Integer.toString(currentStockQuantity));
		} finally {
			accessoMercato.release(); //esce dalla sezione critica
		}
	}
	
}
