package model.Utente;

import model.GestioneCentraleElettrica;

public class UtenteUrgente extends Utente {

	public UtenteUrgente(String nome, GestioneCentraleElettrica g) {
		super(nome, g);
	}
	
	@Override
	public void run() {
		g.richiediUrgente(this, 1, super.getWaitTime());
	}

}
