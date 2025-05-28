package model.Utente;

import java.util.concurrent.ThreadLocalRandom;

import model.CentraleElettrica;

abstract public class Utente extends Thread {

	//attributi
	private static final int MIN_WAIT_TIME = 2000;
	private static final int MAX_WAIT_TIME = 4000;
	private int richiestaAlSecondo;
	private int energiaOttenuta;
	
	CentraleElettrica c; //la centrale alla quale richiede l'energia
	
	//metodo costruttore
	public Utente(String nome, int richiestaAlSecondo, CentraleElettrica c) {
		setName(nome);
		this.c = c;
		this.richiestaAlSecondo = richiestaAlSecondo;
		this.energiaOttenuta = 0;
	}
	
	
	@Override
	public void run() {
		try {
			c.richiedi(this);
			Thread.sleep(getWaitTime());
			c.rilascia(this);
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//metodi get/set
	public int getRichiestaAlSecondo() {
		return richiestaAlSecondo;
	}
	public void setRichiestaAlSecondo(int richiestaAlSecondo) {
		this.richiestaAlSecondo = richiestaAlSecondo;
	}
	public int getEnergiaOttenuta() {
		return energiaOttenuta;
	}
	public void setEnergiaOttenuta(int energiaOttenuta) {
		this.energiaOttenuta = energiaOttenuta;
	}


	protected static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME);
	}


	//metodo toString
	@Override
	public String toString() {
		return "Utente [richiestaAlSecondo=" + richiestaAlSecondo + ", energiaOttenuta=" + energiaOttenuta + ", c=" + c
				+ "]";
	}
	
}
