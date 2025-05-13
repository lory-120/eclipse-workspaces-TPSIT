package model;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import model.Utente.Utente;

public class GestioneCentraleElettrica {

	private String nome;
	private CentraleElettrica centrale;
	private final int MAX_PRELIEVO;
	
	private static final int MIN_WAIT_TIME = 2000;
	private static final int MAX_WAIT_TIME = 4000;
	
	private Lock lock = new ReentrantLock();
	private Condition funzionamentoNormale = lock.newCondition();
	private Condition funzionamentoDiEmergenza = lock.newCondition();
	private boolean centraleInutilizzabile;
	private boolean centraleInEmergenza;
	
	
	public GestioneCentraleElettrica(String nome, CentraleElettrica centrale) {
		this.nome = nome;
		this.centrale = centrale;
		this.centraleInutilizzabile = false;
		this.centraleInEmergenza = false;
	}


	public void richiedi(Utente u, int quantita, int time) {
		lock.lock();
		try {
			while(centraleInutilizzabile || centraleInEmergenza) {
				funzionamentoNormale.await();
			}
			
			System.out.println(u.getName()+" ha richiesto "+quantita+" energia.");
			centrale.prelevaEnergia(quantita);
			Thread.sleep(time);
			System.out.println(u.getName()+" ha prelevato "+quantita+" energia.");
			
			funzionamentoDiEmergenza.signalAll();
			funzionamentoNormale.signalAll();
		} catch(InterruptedException e) {
			System.out.println("Errore nella richiesta: "+e.getMessage());
		} finally {
			lock.unlock();
		}
	}
	
	
	public void richiediUrgente(Utente u, int quantita, int time) {
		lock.lock();
		this.centraleInEmergenza = true;
		try {
			while(centraleInutilizzabile) {
				funzionamentoNormale.await();
				funzionamentoDiEmergenza.await();
			}
			
			//(messaggio di debug)
			Thread.sleep(time);
			centrale.prelevaEnergia(quantita);
			
			
		} catch(InterruptedException e) {
			System.out.println("Errore nella richiesta: "+e.getMessage());
		} finally {
			centraleInEmergenza = false;
			lock.unlock();
		}
	}
	
	
	public void interventoTecnico() {
		lock.lock();
		this.centraleInutilizzabile = true;
		try {
			//tutto il funzionamento della centrale deve essere interrotto
			funzionamentoNormale.await();
			funzionamentoDiEmergenza.await();
			
			//simula la manutenzione
			System.out.println("************* IL TECNICO DEVE FARE MANUTENZIONE! *************");
			for(int i=0; i<=100; i+=10) {
				System.out.println("Manutenzione in corso ("+i+"%)...");
				Thread.sleep(getWaitTime());
			}
			System.out.println("Manutenzione completata. La centrale torna a funzionare come prima.");
			
			funzionamentoDiEmergenza.signalAll();
			funzionamentoNormale.signalAll();
		} catch(InterruptedException e) {
			System.out.println("Manutenzione della centrale +"+this.nome+" interrotta: "+e.getMessage());
		} finally {
			this.centraleInutilizzabile = true;
			lock.unlock();
		}
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	private static int getWaitTime() {
		return ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME, MAX_WAIT_TIME);
	}
	
}
