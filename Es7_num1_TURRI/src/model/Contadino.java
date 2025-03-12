package model;

import java.util.concurrent.ThreadLocalRandom;

import utilities.VenditaNonPermessaException;

public class Contadino extends Thread {

	private MercatoUova mercato;
	private static final int MIN_SLEEP_TIME = 100;
	private static final int MAX_SLEEP_TIME = 450;
	private static final int MIN_UOVA_VENDUTE = 80;
	private static final int MAX_UOVA_VENDUTE = 130;
	private static final int WAIT_TIME = 850;
	
	public Contadino(MercatoUova mercato, String name) {
		this.mercato = mercato;
		setName(name);
	}
	
	
	@Override
	public void run() {
		while(true) {
			try {
				mercato.vendi(getRandomUovaVendute(), getRandomSleepTime());
				Thread.sleep(WAIT_TIME);
			} catch(VenditaNonPermessaException e) {
				System.out.println(e.getMessage());
				try {
					Thread.sleep(WAIT_TIME);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	
	private static int getRandomSleepTime() {
		return ThreadLocalRandom.current().nextInt(MIN_SLEEP_TIME, MAX_SLEEP_TIME+1);
	}
	private static int getRandomUovaVendute() {
		return ThreadLocalRandom.current().nextInt(MIN_UOVA_VENDUTE, MAX_UOVA_VENDUTE+1);
	}
	
}
