package main;

import model.*;

public class MainLaghetto {

	public static void main(String args[]) {
		
		int minPesci = 50;
		int maxPesci = 200;
		int nPescatori = 40;
		int nAddetti = 5;
		
		Laghetto laghetto = new Laghetto(minPesci, maxPesci); //istanzia il laghetto
		
		for(int i=0; i<nAddetti; i++) { //istanzia ed esegui i thread Addetti
			Addetto a = new Addetto(laghetto, i+1);
			a.start();
		}
		
		for(int i=0; i<nPescatori; i++) { //istanzia ed esegui i thread Pescatori
			Pescatore p = new Pescatore(laghetto, i+1);
			p.start();
		}
		
	}
	
}
