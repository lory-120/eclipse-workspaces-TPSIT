package main;

/**
	Una centrale elettrica produce una quantità Q di energia elettrica al secondo.
	Ogni utente fa richiesta di una determinata quantità di corrente al secondo
	(minore di Q) e svolge il suo lavoro solo quando tale quantità gli viene fornita;
	terminato il lavoro, annulla la richiesta. Gli utenti si dividono in normali e
	urgenti: i secondi, che hanno priorità sui primi, sono utenti che gestiscono
	strutture critiche tipo ospedali. Periodicamente un tecnico svolge un lavoro di
	manutenzione che richiede che nessun utente stia usufruendo della corrente.
	Si implementi una soluzione usando il costrutto monitor per modellare la
	centrale elettrica e i processi per modellare gli utenti e il tecnico.
 */

import model.*;
import model.Utente.*;

import java.util.concurrent.ThreadLocalRandom;

public class MainCentraleElettrica {

	public static void main(String[] args) {

		CentraleElettrica centrale = new CentraleElettrica("Centrale bella", 10);
		GestioneCentraleElettrica gestione = new GestioneCentraleElettrica("Gestione fantastica", centrale);
		
		while(true) {
			int i = 0;
			int random = ThreadLocalRandom.current().nextInt(1, 10+1);
			if(random < 6) {
				UtenteNormale utente = new UtenteNormale(("Utente normale "+i), gestione);
				i++;
				utente.start();
			} else if(random >= 6 || random < 10) {
				UtenteUrgente urgente = new UtenteUrgente(("Utente urgente "+i), gestione);
				i++;
				urgente.start();
			} else {
				Tecnico tecnico = new Tecnico(("Tecnico "+i), gestione);
			}
			
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				System.out.println("Errore generale: "+e.getMessage());
			}
		}
		
	}

}
