package main;

import java.util.concurrent.Semaphore;

import model.*;

public class Main {

	public static void main(String[] args) { 
		int capacitaMax=50;
		Semaphore s2=new Semaphore(1);
		Semaphore s3=new Semaphore(0);
		Laghetto l=new Laghetto(capacitaMax, 200, s2, s3);
		int b=0;
		
		Addetto a=new Addetto(l);
		a.start();
		
		while(true) {
			Pescatore p=new Pescatore(l, "Pescatore "+b++);
			p.start();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

}
