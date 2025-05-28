package model.Utente;

import model.CentraleElettrica;

public class UtenteNormale extends Utente {

	public UtenteNormale(String nome, int richiestaAlSecondo, int richiestaTot, CentraleElettrica g) {
		super(nome, richiestaAlSecondo, richiestaTot, g);
	}
	
	@Override
	public void run() {
		c.richiediNormale(this, 1, super.getWaitTime());
	}
	
}
