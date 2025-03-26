package model;

import utilities.OperazioneRifiutataException;

public class Prelievo extends Thread {

	private int quantita;
	private ContoCorrente cc;
	
	public Prelievo(ContoCorrente cc, int quantita) {
		this.cc = cc;
		this.quantita = quantita;
	}
	
	public void run() {
		try {
			cc.preleva(quantita);
		} catch(OperazioneRifiutataException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
