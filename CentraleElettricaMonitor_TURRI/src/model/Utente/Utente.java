package model.Utente;

import java.util.concurrent.ThreadLocalRandom;

import model.GestioneCentraleElettrica;

abstract public class Utente extends Thread {

	private static final int MIN_WAIT_TIME = 2000;
	private static final int MAX_WAIT_TIME = 4000;
	
	GestioneCentraleElettrica g;
	
	public Utente(String nome, GestioneCentraleElettrica g) {
		setName(nome);
		this.g = g;
	}
	
	
	protected static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME);
	}
	
}
