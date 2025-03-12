package main;

import java.util.concurrent.ThreadLocalRandom;

import model.*;
import model.Persona.*;

public class MainToiletDoppioAccesso {

	public static void main(String[] args) {

		ToiletDoppioAccesso toilet = new ToiletDoppioAccesso();
		
		int indexUomo = 0;
		int indexDonna = 0;
		
		while(true) {
			if(ThreadLocalRandom.current().nextBoolean()) {
				Uomo u = new Uomo(("Uomo "+(indexUomo+1)), toilet);
				indexUomo++;
				
				//System.out.println("avvio di un uomo...");
				
				u.start();
			} else {
				Donna d = new Donna(("Donna "+(indexDonna+1)), toilet);
				
				//System.out.println("avvio di una donna...");
				
				indexDonna++;
				d.start();
			}
			
			try {
				//System.out.println("ora c'è la pausa nel main...");
				Thread.sleep(230);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
