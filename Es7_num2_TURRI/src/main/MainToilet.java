package main;

import model.*;

public class MainToilet {

	public static void main(String args[]) {
		
		Toilet toilet = new Toilet();
		
		int index = 0;
		
		while(true) {
			Persona p = new Persona(toilet, "Persona "+index++);
			p.start();
			try {
				Thread.sleep(200);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
