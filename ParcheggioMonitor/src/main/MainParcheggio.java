package main;

import model.*;

public class MainParcheggio {

	public static void main(String[] args) {
		
		Parcheggio p = new Parcheggio(4);
		int i = 0;
		
		while(true) {
			Auto a = new Auto(p, "Auto "+i++);
			a.start();
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}

}
