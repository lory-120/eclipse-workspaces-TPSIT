package model;

import utilities.BufferVuotoException;

/*
 * Preleva il numero, dopo aver verificato che il
 * buffer non è vuoto.
 * Se ci riesci, azzeri il buffer.
 * Sennò, non prelevi niente.
 * */

public class Consumatore extends Thread {
	
	//tempo per il quale il thread non preleva
	private Buffer buffer;
	private int waitTime;
	private final int processId;
	
	//metodo costruttore
	public Consumatore(Buffer buffer, int waitTime, int processId) {
		this.buffer = buffer;
		this.waitTime = waitTime;
		this.processId = processId;
	}
	
	
	//metodi della funzione
	public void run() {
		while(true) {
			try {
				if(!buffer.isEmpty()) {
					printSquaredNumber(buffer.getLast());
					buffer.reset();
				}
				Thread.sleep(waitTime);
			} catch(BufferVuotoException e) {
				System.out.println(e.getMessage());
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void printSquaredNumber(int n) {
		System.out.println("----------------------------");
		System.out.println("Sono il thread " + this.processId);
		System.out.println("Numero: " + n + ". Quadrato del numero: " + n*n + ".");
		System.out.println("----------------------------");
	}
	
}
