package model;

import java.util.NoSuchElementException;

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
	
	/*
	 * - prendi il numero dal buffer
	 * - se trovi il numero, fai il printSquaredNumber()
	 * - se trovi null, fai niente
	*/
	public void run() {
		while(true) {
			try {
				Integer tmp = buffer.getLast();
				if(tmp != null) {
					this.printSquaredNumber(tmp);
					buffer.reset();
				}
				Thread.sleep(waitTime);
			} catch(BufferVuotoException e) {
				try {
					Thread.sleep(waitTime);
				} catch(InterruptedException e1) {
					System.out.println("InterruptedExcpetion dopo il buffer vuoto: " + e1.getMessage());
				}
			} catch(InterruptedException e) {
				System.out.println("InterruptedException: " + e.getMessage());
			} catch(NoSuchElementException e) {
				System.out.println("NoSuchElementException: " + e.getMessage());
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
