package model;

import java.util.Vector;
import utilities.BufferVuotoException;

public class Buffer {

	//attributi
	private Vector<Integer> buffer;
	private int index;
	
	//metodo costruttore
	public Buffer() {
		this.buffer = new Vector<>(1, 5);
		this.index = 0;
	}
	
	
	//metodi della funzione
	public void give(Integer n) {
		buffer.add(n);
		index++;
	}
	
	public Integer getLast() throws BufferVuotoException {
		if(index == 0) {
			throw new BufferVuotoException("Il buffer è vuoto.");
		}
		Integer tmp = buffer.lastElement();
		buffer.remove(index-1);
		index--;
		return tmp;
	}	
	
	public boolean isEmpty() {
		return buffer.isEmpty();
	}
	
	public synchronized void reset() {
		buffer.removeAllElements();
		this.index = 0;
	}
	
}
