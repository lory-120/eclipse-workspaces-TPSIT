package model;

import utilities.OperazioneRifiutataException;

public class ContoCorrente {

	//attributo
	private double saldo;
	
	//metodo costruttore
	public ContoCorrente(int saldoIniziale) {
		this.saldo = saldoIniziale;
	}
	public ContoCorrente() {
		this.saldo = 0;
	}

	//metodi get/set
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	
	//metodi della funzione
	public synchronized void preleva(int q) throws OperazioneRifiutataException {
		if(q < 0) throw new OperazioneRifiutataException("Non puoi prelevare "+q+"€. E' un numero negativo.");
		if(this.saldo - q < 0) throw new OperazioneRifiutataException("Prelievo di "+q+"€ non consentito. Saldo attuale: "+this.saldo);
		this.saldo -= q;
		System.out.println("Effettuato prelievo di "+q+"€. Saldo attuale: "+saldo);
	}
	
	public synchronized void versa(int q) throws OperazioneRifiutataException {
		if(q < 0) throw new OperazioneRifiutataException("Non puoi versare "+q+"€. E' un numero negativo.");
		this.saldo += q;
		System.out.println("Effettuato versamento di "+q+"€. Saldo attuale: "+saldo);
	}
	
}
