package model;

import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import utilities.BufferVuotoException;

public class Buffer {

	//attributi
	private Vector<Integer> buffer;
	private int index;
	private Semaphore accessoBufferSemaforo;
	
	//metodo costruttore
	public Buffer() {
		this.buffer = new Vector<>(1, 5);
		this.index = 0;
		accessoBufferSemaforo = new Semaphore(1);
	}
	
	
	//metodi della funzione
	public void give(Integer n) throws InterruptedException {
		accessoBufferSemaforo.acquire(); //occupa il buffer
		buffer.add(n); //sezione critica
		index++;
		accessoBufferSemaforo.release(); //rilascia il buffer
	}
	
	public Integer getLast() throws BufferVuotoException, InterruptedException, NoSuchElementException {
		accessoBufferSemaforo.acquire(); //occupa il buffer
		
		//sezione critica del codice, si accede alla risorsa del buffer
		try {
			if(this.isEmpty()) {
				throw new BufferVuotoException("Il buffer è vuoto.");
			}
			Integer tmp = buffer.lastElement();
			buffer.remove(index-1);
			index--;
			
			return tmp;
		} finally {
			accessoBufferSemaforo.release(); //rilascia il buffer
		}
	}	
	
	public boolean isEmpty() {
		return buffer.isEmpty();
	}
	
	public void reset() throws InterruptedException {
		accessoBufferSemaforo.acquire(); //occupa il buffer
		
		try { //sezione critica
			buffer.removeAllElements();
			this.index = 0;
		} finally {
			accessoBufferSemaforo.release(); //rilascia il buffer
		}
	}
	
}
