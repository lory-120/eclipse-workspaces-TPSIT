package model.Utente;

import model.CentraleElettrica;

public class Tecnico extends Thread {

	CentraleElettrica c;
	
	public Tecnico(String nome, CentraleElettrica c) {
		setName(nome);
		this.c = c;
	}
	
	
	@Override
	public void run() {
		try {
			c.iniziaManutenzione(this);
			
			System.out.println("*** IL TECNICO STA FACENDO MANUTENZIONE ***");
			for(int i=0; i<=100; i+=10) {
				System.out.println("Manutenzione in corso... ("+i+"%)");
				Thread.sleep(500);
			}
			
			c.fineManutenzione(this);			
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
