package model;

import java.util.concurrent.ThreadLocalRandom;

import utilities.MercatoVuotoException;

public class Grossista extends Thread {

	//attributi
	private static final int MAX_QUINTALI = 7;
	private static final int MIN_QUINTALI = 1;
	private static final int MAX_WAIT_TIME = 800;
	private static final int MIN_WAIT_TIME = 300;
	private static final int PAUSE_TIME = 1000;
	private final int waitTime;
	private final int GROSSISTA_ID;
	private Mercato mercato;
	
	
	//metodo costruttore
	public Grossista(Mercato mercato, int GROSSISTA_ID) {
		this.waitTime = ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME+1);
		this.mercato = mercato;
		this.GROSSISTA_ID = GROSSISTA_ID;
	}
	
	
	//metodi della funzione
	public void run() {
		while(true) {
			try {
				mercato.acquista(getRandomQuintali(), this.waitTime, this.GROSSISTA_ID);
				Thread.sleep(PAUSE_TIME);
			} catch(MercatoVuotoException e) {
				System.out.println(e.getMessage());
				try {
					Thread.sleep(PAUSE_TIME);
				} catch(InterruptedException e1) {
					System.out.println(e1.getMessage());
				}
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	//prendi un numero random di quintali da acquistare
	private static int getRandomQuintali() {
		return ThreadLocalRandom.current().nextInt(MIN_QUINTALI, MAX_QUINTALI+1);
	}
	
}
