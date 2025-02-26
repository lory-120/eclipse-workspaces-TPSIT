package model;

import java.util.concurrent.ThreadLocalRandom;

import utilities.MercatoPienoException;

public class Produttore extends Thread {

	//attributi
	private static final int MAX_QUINTALI = 10;
	private static final int MIN_QUINTALI = 1;
	private static final int MAX_WAIT_TIME = 800;
	private static final int MIN_WAIT_TIME = 300;
	private static final int PAUSE_TIME = 1000;
	private final int waitTime;
	private final int PRODUTTORE_ID;
	private Mercato mercato;
	
	
	//metodo costruttore
	public Produttore(Mercato mercato, int PRODUTTORE_ID) {
		this.waitTime = ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME+1);
		this.mercato = mercato;
		this.PRODUTTORE_ID = PRODUTTORE_ID;
	}
	
	
	//metodi della funzione
	public void run() {
		while(true) {
			try {
				mercato.vendi(getRandomQuintali(), this.waitTime, this.PRODUTTORE_ID);
				Thread.sleep(PAUSE_TIME);
			} catch(MercatoPienoException e) {
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
	
	//prendi un numero random di quintali da vendere
	private static int getRandomQuintali() {
		return ThreadLocalRandom.current().nextInt(MIN_QUINTALI, MAX_QUINTALI+1);
	}
	
}
