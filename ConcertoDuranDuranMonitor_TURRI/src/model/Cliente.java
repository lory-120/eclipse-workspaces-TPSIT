package model;

import java.util.concurrent.ThreadLocalRandom;

public class Cliente extends Thread {

	private static final int MAX_WAIT_TIME = 1000;
	private static final int MIN_WAIT_TIME = 3000;
	private boolean isIscritto; //true se il cliente è iscritto al fan club
	private Cassa c;
	
	public Cliente(String nome, boolean isIscritto, Cassa c) {
		setName(nome);
		this.isIscritto = isIscritto;
		this.c = c;
	}
	
	
	@Override
	public void run() {
		 c.acquistaBiglietto(this, getWaitTime());
	}

	public boolean isIscritto() {
		return isIscritto;
	}
	
	private static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MAX_WAIT_TIME, MIN_WAIT_TIME+1);
	}
	
}
