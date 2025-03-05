package model;

import java.util.Random;
import java.util.concurrent.Semaphore;

import model.exception.PesciNonDisponibiliException;

public class Laghetto {
	private final int MAX_PESCI; //200
	private final int MIN_PESCI; //50
	private int numeroPesci; //pesci effettivamente presenti nel laghetto
	private Random r; //per generare lo sleep randomico
	private Semaphore pescaPermessa;
	private Semaphore ripopolamentoPermesso;
	
	public Laghetto(int min, int max, Semaphore s1, Semaphore s2) {
		this.MIN_PESCI=min;
		this.MAX_PESCI=max;
		r=new Random();
		this.numeroPesci=(MAX_PESCI-MIN_PESCI)/2;
		this.pescaPermessa=s1;
		this.ripopolamentoPermesso=s2;
	}

	public int getNumeroPesci() {
		return numeroPesci;
	}

	public void setNumeroPesci(int numeroPesci) {
		this.numeroPesci = numeroPesci;
	}

	public Random getR() {
		return r;
	}

	public void setR(Random r) {
		this.r = r;
	}

	public Semaphore getPescaPermessa() {
		return pescaPermessa;
	}

	public void setPescaPermessa(Semaphore pescaPermessa) {
		this.pescaPermessa = pescaPermessa;
	}

	public Semaphore getRipopolamentoPermesso() {
		return ripopolamentoPermesso;
	}

	public void setRipopolamentoPermesso(Semaphore ripopolamentoPermesso) {
		this.ripopolamentoPermesso = ripopolamentoPermesso;
	}

	public int getMAX_PESCI() {
		return MAX_PESCI;
	}

	public int getMIN_PESCI() {
		return MIN_PESCI;
	}
	
	public void inizia(int t) throws InterruptedException, PesciNonDisponibiliException {
		if(t==0) {
			pescaPermessa.acquire();//blocco gli altri pescatori
			if(numeroPesci <=MIN_PESCI) {
				ripopolamentoPermesso.release();//rilascio l'addetto
				throw new PesciNonDisponibiliException("!!!!! Pesci presenti: "+numeroPesci);
			}
			
			numeroPesci--;
			System.out.println(Thread.currentThread().getName()+" ha pescato! Pesci presenti: "+numeroPesci);
			Thread.sleep(r.nextInt(801)+200);
			
		} else { //ramo ripopolamento
			ripopolamentoPermesso.release();// blocchiamo gli altrui thread
			if(numeroPesci>=MAX_PESCI) {
				ripopolamentoPermesso.acquire(); //blocco l'addetto
				throw new PesciNonDisponibiliException("Numero massimo di pesci raggiunto!!");
			}
			numeroPesci+=10;
			System.out.println(Thread.currentThread().getName()+" ha rifornito il laghetto! Pesci presenti: "+numeroPesci);
			Thread.sleep(r.nextInt(601)+300);
		}
	}
	
	public void finisci(int t) throws InterruptedException {
		try {
			if(t==0) {
				Thread.sleep(1000);
				pescaPermessa.release();
			} else {
				Thread.sleep(3000);
				ripopolamentoPermesso.acquire();
			} 
		} catch(InterruptedException e) {
			throw e;
		} finally {
			if(numeroPesci>50) {
				pescaPermessa.release();
			}
		}
	}
}
