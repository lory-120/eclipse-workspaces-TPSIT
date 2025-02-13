package model;

import java.util.concurrent.Semaphore;

import utilities.OperazioneNonConsentitaException;

public class ContoCorrente {
	
	private double saldo;
	private Semaphore semaforo = new Semaphore(1);
	
	public ContoCorrente(double saldoIniziale) {
		this.saldo = saldoIniziale;
	}
	
	
	public double versamento(double importo) throws OperazioneNonConsentitaException, InterruptedException {
		try {
			semaforo.acquire(); //da qua si entra nella sezione critica
			
			//l'accesso all'importo e al saldo e all'importo sono sezioni critiche del codice, quindi si gestiscono coi semafori
			if(importo < 0) {
				throw new OperazioneNonConsentitaException("L'importo non può essere negativo.");
			}
			saldo += importo;
			
			semaforo.release(); //si esce dalla sezione critica
			return saldo;
		} catch(InterruptedException e) {
			throw e;
		}
	}
	
	public double prelievo(double importo) throws OperazioneNonConsentitaException, InterruptedException {

		try {
			semaforo.acquire(); //da qua si entra nella sezione critica
			
			//l'accesso all'importo e al saldo e all'importo sono sezioni critiche del codice, quindi si gestiscono coi semafori
			if(importo < 0) {
				throw new OperazioneNonConsentitaException("L'importo non può essere negativo.");
			}
			if(importo > saldo) {
				throw new OperazioneNonConsentitaException("Non puoi prelevare più di quel che hai.");
			}
			saldo -= importo;
			
			semaforo.release(); //si esce dalla sezione critica
			return saldo;
		} catch(InterruptedException e) {
			throw e;
		}
		
	}

	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public Semaphore getSemaforo() {
		return semaforo;
	}
	public void setSemaforo(Semaphore semaforo) {
		this.semaforo = semaforo;
	}
	
}
