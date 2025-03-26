package model;

public class Parcheggio {

	private final int MAX_POSTI;
	private int postiOccupati;
	private int queue;
	
	public Parcheggio(int MAX_POSTI) {
		this.MAX_POSTI = MAX_POSTI;
		this.postiOccupati = 0;
		this.queue = 0;
	}
	
	public synchronized void park() throws InterruptedException {
		String name = Thread.currentThread().getName();
		
		queue++;
		
		while(postiOccupati == MAX_POSTI) {
			System.out.println(name + " si è messo in fila. Auto in fila per parcheggiare: " + queue);
			wait();
		}
		queue--;
		
		
		postiOccupati++;
		
		System.out.println(name + " ha iniziato a parcheggiare.");
	}
	
	public synchronized void leave() {
		String name = Thread.currentThread().getName();
		postiOccupati--;
		System.out.println(name + " ha liberato un posto. Posti disponibili: " + (MAX_POSTI-postiOccupati));
		notifyAll();
	}
	
}
