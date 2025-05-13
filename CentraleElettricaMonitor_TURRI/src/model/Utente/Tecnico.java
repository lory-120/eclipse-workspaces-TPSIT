package model.Utente;

import model.GestioneCentraleElettrica;

public class Tecnico extends Thread {

	GestioneCentraleElettrica g;
	
	public Tecnico(String nome, GestioneCentraleElettrica g) {
		setName(nome);
		this.g = g;
	}
	
}
