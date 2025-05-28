package model.Utente;

import model.CentraleElettrica;

public class UtenteUrgente extends Utente {

	public UtenteUrgente(String nome, int richiestaAlSecondo, int richiestaTot, CentraleElettrica g) {
		super(nome, richiestaAlSecondo, richiestaTot, g);
	}
	
	@Override
	public void run() {
		c.richiediUrgente(this, 1, super.getWaitTime());
	}

}
