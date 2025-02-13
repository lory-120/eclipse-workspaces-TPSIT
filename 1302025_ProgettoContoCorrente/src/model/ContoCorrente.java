package model;

import java.util.concurrent.Semaphore;

import model.exception.OperazioneNonConsentitaException;

public class ContoCorrente {
	private double saldo;
	private Semaphore semaforo=new Semaphore(1);
	
	public ContoCorrente(double saldoIniziale) {
		this.saldo=saldoIniziale;
	}
	
	public double versamento(double importo) throws OperazioneNonConsentitaException, InterruptedException {
		semaforo.acquire();
		if(importo<0) {
			semaforo.release();
			throw new OperazioneNonConsentitaException("Operazione non consentita!L'importo deve essere positivo");
		}
		saldo+=importo;
		semaforo.release();
		return saldo;
	}
	
	public double prelievo(double importo) throws OperazioneNonConsentitaException, InterruptedException {
		try {
			semaforo.acquire();
			if(importo<0) {
				semaforo.release();
				throw new OperazioneNonConsentitaException("Operazione non consentita!L'importo deve essere positivo");
			}
			if(saldo >=importo) {
				saldo-=importo;
				return saldo;
			} else {
				throw new OperazioneNonConsentitaException("Fondi non sufficienti per effettuare l'operzione!");
			}
		} catch (InterruptedException e) {
			throw e;
		} finally {
			semaforo.release();
		}
		
		
		
	}
	
}
