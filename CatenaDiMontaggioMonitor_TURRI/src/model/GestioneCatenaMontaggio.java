package model;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.*;

public class GestioneCatenaMontaggio {

	private static final int MIN_WAIT_TIME = 2000;
	private static final int MAX_WAIT_TIME = 4000;
	
	private final int CAPACITA_SCATOLA, CAPACITA_MAX;
	private int schedeAssemblate;
	
	private Lock lock = new ReentrantLock();
	private Condition ok_assemblaggio = lock.newCondition();
	private Condition ok_inscatolamento = lock.newCondition();
	
	public GestioneCatenaMontaggio(int capacitaScatola, int capacitaMax) {
		this.CAPACITA_SCATOLA = capacitaScatola;
		this.CAPACITA_MAX = capacitaMax;
		this.schedeAssemblate = 0;
	}
	
	public void assembla() throws InterruptedException {
		lock.lock();
		
		try {
			while(schedeAssemblate >= CAPACITA_MAX) {
				ok_assemblaggio.await();
			}
			
			this.schedeAssemblate++;
			System.out.println("Assemblata 1 scheda. Schede nel magazzino: "+this.schedeAssemblate);
			
			if(schedeAssemblate >= CAPACITA_SCATOLA) {
				ok_inscatolamento.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void inscatola() throws InterruptedException {
		lock.lock();
		
		try {
			while(schedeAssemblate < CAPACITA_SCATOLA) {
				ok_inscatolamento.await();
			}
			
			try {
				Thread.sleep(getWaitTime());
			} catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
			schedeAssemblate -= CAPACITA_SCATOLA;
			System.out.println("Fatta una scatola da "+this.CAPACITA_SCATOLA+". Schede disponibili: "+this.schedeAssemblate);
			
			ok_assemblaggio.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	private static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME);
	}
	
}
