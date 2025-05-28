package model;

import java.util.concurrent.ThreadLocalRandom;

public class CentraleElettrica extends Thread {

	private String nome;
	private final int produzioneAlSecondo;
	private int quantita;
	
	private static final int MIN_WAIT_TIME = 1000;
	private static final int MAX_WAIT_TIME = 2500;
	
	public CentraleElettrica(String nome, int produzioneAlSecondo) {
		setName(nome);
		this.produzioneAlSecondo = produzioneAlSecondo;
	}


	public void run() {
		try {
			quantita += produzioneAlSecondo;
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public void prelevaEnergia(int quantitaRichiesta) {
		try {
			for(int i=0; i<=quantitaRichiesta; i++) {
				System.out.println("Produzione energia richiesta ("+i+"/"+quantitaRichiesta+")...");
				Thread.sleep(getWaitTime());
			}
		} catch(InterruptedException e) {
			System.out.println("Produzione energia interrotta: "+e.getMessage());
		}
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQuantita() {
		return quantita;
	}
	
	
	private static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME);
	}

}
