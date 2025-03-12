package model;

import java.util.concurrent.ThreadLocalRandom;

import utilities.AcquistoNonPermessoException;

public class GrossistaUova extends Thread {

	private MercatoUova mercato;
	private static final int MIN_SLEEP_TIME = 100;
	private static final int MAX_SLEEP_TIME = 450;
	private static final int WAIT_TIME = 1000;
	private static final int N_UOVA_ACQUISTATE = 50;
	
	public GrossistaUova(MercatoUova mercato, String name) {
		this.mercato = mercato;
		setName(name);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				mercato.acquista(N_UOVA_ACQUISTATE, getRandomSleepTime());
				Thread.sleep(WAIT_TIME);
			} catch(AcquistoNonPermessoException e) {
				System.out.println(e.getMessage());
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	
	private static int getRandomSleepTime() {
		return ThreadLocalRandom.current().nextInt(MIN_SLEEP_TIME, MAX_SLEEP_TIME+1);
	}
	
}
