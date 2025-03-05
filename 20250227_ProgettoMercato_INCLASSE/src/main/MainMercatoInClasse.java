package main;

import java.util.concurrent.Semaphore;
import model.*;

public class MainMercatoInClasse {

	public static void main(String args[]) {
		
		MercatoOrtofrutticolo m = new MercatoOrtofrutticolo(95, 5, 10, new Semaphore(95), new Semaphore(40));
		
		for(int i=0; i<10; i++) {
			Grossista g = new Grossista(Integer.toString(i+1), 10, m);
			g.start();
		}
		
		for(int i=0; i<5; i++) {
			Produttore p = new Produttore(Integer.toString(i+1), 5, m);
			p.start();
		}
		
	}
	
}
