package model;

import model.exception.QuantitaException;

public class Grossista extends Thread{
	private int quantita;
	private MercatoOrtofrutticolo m;
	private boolean acquisto;
	private boolean printed;
	public Grossista(String name, int quantita, MercatoOrtofrutticolo m) {
		setName(name);
		this.quantita=quantita;
		this.m=m;
		acquisto=false;
		printed=false;
	}
	@Override
	public void run() {
	
		while(!acquisto) {
			try {
				m.acquista(quantita);
				acquisto=true;
			} catch (QuantitaException | InterruptedException e) {
				if(printed==false) {
					System.out.println(e.getMessage());
					printed=true;
				}
				Thread.yield();
			}
		}
		
	}
	
}
