package main;

import model.Contadino;
import model.GrossistaUova;
import model.MercatoUova;

public class MainContadiniCheVendonoUova {

	public static void main(String args[]) {
		
		MercatoUova mercato = new MercatoUova(2000);
		
		for(int i=0; i<5; i++) {
			Contadino c = new Contadino(mercato, "Contadino "+i);
			c.start();
		}
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<3; i++) {
			GrossistaUova g = new GrossistaUova(mercato, "Grossista "+i);
			g.start();
		}
		
	}
	
}
