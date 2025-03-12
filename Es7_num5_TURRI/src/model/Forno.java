package model;

import java.util.concurrent.Semaphore;

public class Forno {

	private Semaphore accessoForno;
	
	public Forno() {
		this.accessoForno = new Semaphore(1);
	}
	
	
	protected void cuociPizza(int cookingTime) throws InterruptedException {
		String name = Thread.currentThread().getName();
		
		if(!accessoForno.tryAcquire()) {
			System.out.println("Si deve cuocere la pizza " + name + ", ma ci sono ancora "
					+ accessoForno.getQueueLength() + " pizze da cuocere.");
			accessoForno.acquire();
		}
		
		try {
			System.out.println("La pizza " + name + " ha iniziato a cuocere.");
			Thread.sleep(cookingTime);
			System.out.println("La pizza " + name + " ha finito di cuocere.");
		} finally {
			accessoForno.release();
		}
	}
	
	
}
