package main;

import model.*;

public class MainToilet {

	public static void main(String args[]) {
		
		Toilet t = new Toilet();
		int i = 0;
		
		while(true) {
			Persona p = new Persona(t, "Persona "+i++);
			p.start();
			try {
				Thread.sleep(400);
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
}
