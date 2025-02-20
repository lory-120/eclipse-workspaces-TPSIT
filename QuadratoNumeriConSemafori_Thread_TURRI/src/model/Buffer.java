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
		try {
			accessoBufferSemaforo.acquire(); //occupa il buffer
			buffer.add(n);
			index++;
			accessoBufferSemaforo.release(); //rilascia il buffer
		} catch(InterruptedException e) {
			throw e;
		}
	}
	
	public Integer getLast() throws BufferVuotoException, InterruptedException, NoSuchElementException {
		if(this.isEmpty()) {
			throw new BufferVuotoException("Il buffer è vuoto.");
		}
		try {
			accessoBufferSemaforo.acquire(); //occupa il buffer
			Integer tmp = buffer.lastElement();
			buffer.remove(index-1);
			index--;
			accessoBufferSemaforo.release(); //rilascia il buffer
			return tmp;
		} catch(InterruptedException e) {
			throw e;
		} catch(NoSuchElementException e) {
			throw e;
		}
	}	
	
	public boolean isEmpty() {
		return buffer.isEmpty();
	}
	
	public synchronized void reset() {
		buffer.removeAllElements();
		this.index = 0;
	}
	
}
