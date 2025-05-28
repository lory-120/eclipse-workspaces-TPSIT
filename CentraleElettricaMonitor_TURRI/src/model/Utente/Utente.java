package model.Utente;

import java.util.concurrent.ThreadLocalRandom;

import model.CentraleElettrica;

abstract public class Utente extends Thread {

	private static final int MIN_WAIT_TIME = 2000;
	private static final int MAX_WAIT_TIME = 4000;
	private int richiestaAlSecondo;
	private int richiestaTot;
	private int energiaOttenuta;
	
	CentraleElettrica c;
	
	public Utente(String nome, int richiestaAlSecondo, int richiestaTot, CentraleElettrica c) {
		setName(nome);
		this.c = c;
		this.richiestaAlSecondo = richiestaAlSecondo;
		this.richiestaTot = richiestaTot;
		this.energiaOttenuta = 0;
	}
	
	
	protected static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME);
	}
	
}
