package model;

import java.util.concurrent.Semaphore;

public class Produttore extends Thread {

	private Buffer buffer;
	private int waitTime;
	private int n;
	private Semaphore produttoreMutex;
	
	public Produttore(Buffer buffer, int waitTime) {
		this.buffer = buffer;
		this.n = 0;
		this.waitTime = waitTime;
		this.produttoreMutex = new Semaphore(1);
	}
	
	private int getN() {
		this.n++;
		return this.n;
	}
	
	
	public void run() {
		while(true) {
			try {
				produttoreMutex.acquire(); //appropriati del produttore
				buffer.give(this.getN()); //sezione critica
				produttoreMutex.release(); //rilascia la risorsa produttore
				
				Thread.sleep(waitTime);
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
}
