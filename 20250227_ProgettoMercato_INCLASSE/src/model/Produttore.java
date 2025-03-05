package model;

import model.exception.QuantitaException;

public class Produttore extends Thread{
	private int quantita;
	private MercatoOrtofrutticolo m;
	private boolean vendita;
	private boolean printed;
	public Produttore(String name, int quantita, MercatoOrtofrutticolo m) {
		setName(name);
		this.quantita=quantita;
		this.m=m;
		vendita=false;
		printed=false;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public MercatoOrtofrutticolo getM() {
		return m;
	}

	public void setM(MercatoOrtofrutticolo m) {
		this.m = m;
	}
	
	@Override
	public void run() {
		while(!vendita) {
			try {
				m.vendi(quantita);
				vendita=true;
			} catch (QuantitaException | InterruptedException e) {
				if(printed==false) {
					System.out.println(e);
					printed=true;
				}
				Thread.yield();
			} 
		}
		
	}
}
