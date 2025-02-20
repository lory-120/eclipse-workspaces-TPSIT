package model;

public class Produttore extends Thread {

	private Buffer buffer;
	private int waitTime;
	private int n;
	
	public Produttore(Buffer buffer, int waitTime) {
		this.buffer = buffer;
		this.n = 0;
		this.waitTime = waitTime;
	}
	
	public int getN() {
		this.n++;
		return this.n;
	}
	
	
	public void run() {
		while(true) {
			try {
				buffer.give(this.getN());
				Thread.sleep(waitTime);
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
}
