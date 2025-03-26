package model;

import utilities.OperazioneRifiutataException;

public class Versamento extends Thread {

	private int quantita;
	private ContoCorrente cc;
	
	public Versamento(ContoCorrente cc, int quantita) {
		this.cc = cc;
		this.quantita = quantita;
	}

	public void run() {
		try {
			cc.versa(quantita);
		} catch(OperazioneRifiutataException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
