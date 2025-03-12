package main;

import model.Automobile;
import model.Parcheggio;

public class MainParcheggio {

	public static void main(String[] args) {

		//***POSTI DECISI DALL'UTENTE***
		int nPosti = 5;
		
		Parcheggio p = new Parcheggio(nPosti);
		
		int index = 1;	
		while(true) {
			Automobile a = new Automobile("Auto "+index, p);
			index++;
			a.start();
			
			try {
				Thread.sleep(200);
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}

}
