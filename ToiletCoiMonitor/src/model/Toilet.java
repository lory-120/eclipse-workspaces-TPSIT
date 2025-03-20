package model;

public class Toilet {
	
	private boolean isOccupied;
	
	public Toilet() {
		this.isOccupied = false;
	}

	public synchronized void usaBagno() {
		String name = Thread.currentThread().getName();
		
		while(isOccupied) {
			System.out.println(name + " si è messo in fila.");
			try {
				wait();
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		isOccupied = true;
		
		System.out.println(name + " sta usando il bagno.");
	}
	
	public synchronized void liberaBagno() {
		String name = Thread.currentThread().getName();
		this.isOccupied = false;
		System.out.println(name + " ha liberato il bagno.");
		notifyAll();
	}
	
}
