package model;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import model.Utente.*;

public class CentraleElettrica {

	//attributi
	private String nome;
	private final int produzioneAlSecondo; //(Q)
	private int quantitaDisponibile;
	private ReentrantLock lock;
    private Condition condClientiNormali;
    private Condition condClientiUrgenti;
    private Condition condTecnico;
    private boolean manutenzioneInCorso; //se è true, allora vil tecnico deve fare o sta facendo manutenzione
	
    
	//metodo costruttore
	public CentraleElettrica(String nome, int produzioneAlSecondo) {
		this.nome = nome;
		this.produzioneAlSecondo = produzioneAlSecondo;
		this.quantitaDisponibile = produzioneAlSecondo;
		this.lock = new ReentrantLock();
		this.condClientiNormali = lock.newCondition();
		this.condClientiUrgenti = lock.newCondition();
		this.condTecnico = lock.newCondition();
		this.manutenzioneInCorso = false;
	}
	
	public void richiedi(Utente u) throws InterruptedException {
		lock.lock();
		try {
			while(quantitaDisponibile < u.getRichiestaAlSecondo() || manutenzioneInCorso) {
				System.out.println("L'utente "+u.getName()+" si è messo in attesa.");
				if(u instanceof UtenteUrgente) {
				    condClientiUrgenti.await();
				} else {
				    condClientiNormali.await();
				}
			}
			
			if(u instanceof UtenteUrgente) {
				System.out.println("Cliente Urgente "+u.getName()+" sta usando "+u.getRichiestaAlSecondo()+" energia.");
			} else if(u instanceof UtenteNormale) {
				System.out.println("Cliente "+u.getName()+" sta usando "+u.getRichiestaAlSecondo()+" energia.");
			} else {
				throw new RuntimeException("Tipo di utente non riconosciuto: " + u.toString());
			}
			
			this.quantitaDisponibile -= u.getRichiestaAlSecondo();
		} finally {
			lock.unlock();
		}
	}
	
	
	public void rilascia(Utente u) throws InterruptedException {
		lock.lock();
		try {
			this.quantitaDisponibile += u.getRichiestaAlSecondo();
			System.out.print((u instanceof UtenteUrgente) ? "Utente urgrnte " : "Utente normale ");
			System.out.println(u.getName()+" ha rilasciato "+u.getRichiestaAlSecondo()+" energia.");
			
			condTecnico.signalAll();
			condClientiUrgenti.signalAll();
			condClientiNormali.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	
	public void iniziaManutenzione(Tecnico t) throws InterruptedException {
		lock.lock();
		try {
			this.manutenzioneInCorso = true;
			System.out.println("Il tecnico "+t.getName()+" deve fare manutenzione.");
			
			//se questa condizione è vera, allora nessuno sta usando la centrale
			while(quantitaDisponibile != produzioneAlSecondo) {
				condTecnico.await();
			}
		} finally {
			lock.unlock();
		}
	}
	
	
	public void fineManutenzione(Tecnico t) throws InterruptedException {
		lock.lock();
		try {
			System.out.println("Il tecnico "+t.getName()+" ha terminato la sua manutenzione.");
			this.manutenzioneInCorso = false;
			
			condClientiUrgenti.signalAll();
			condClientiNormali.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
