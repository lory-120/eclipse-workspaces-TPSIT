package p2;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class TutorLaboratorio {
    private static final int NUM_COMPUTER = 20;
    private final ReentrantLock lock;
    
    // Array per tracciare lo stato dei computer (true = occupato, false = libero)
    private final boolean[] computer;
    
    // Condition variables per i diversi tipi di utenti
    private final Condition codaProfessori;
    private final Condition codaTesisti;
    private final Condition codaStudenti;
    
    // Contatori per gli utenti in attesa (per gestire le priorità)
    private int professoriInAttesa;
    private int tesistiInAttesa;
    private int studentiInAttesa;
    
    // Stato del laboratorio
    private boolean professorePresente;
    private int computerOccupati;
    
    public TutorLaboratorio() {
        this.lock = new ReentrantLock();
        this.computer = new boolean[NUM_COMPUTER + 1]; // Indici da 1 a 20
        
        this.codaProfessori = lock.newCondition();
        this.codaTesisti = lock.newCondition();
        this.codaStudenti = lock.newCondition();
        
        this.professoriInAttesa = 0;
        this.tesistiInAttesa = 0;
        this.studentiInAttesa = 0;
        
        this.professorePresente = false;
        this.computerOccupati = 0;
        
        // Inizializza tutti i computer come liberi
        for (int i = 1; i <= NUM_COMPUTER; i++) {
            computer[i] = false;
        }
    }
    
    public void richiestaAccessoProfessore(int idProfessore) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Professore " + idProfessore + " richiede accesso esclusivo al laboratorio");
            
            professoriInAttesa++;
            
            // Attende finché non può accedere in modo esclusivo
            while (!possoAccedereProfessore()) {
                codaProfessori.await();
            }
            
            professoriInAttesa--;
            
            // Occupa tutto il laboratorio
            professorePresente = true;
            for (int i = 1; i <= NUM_COMPUTER; i++) {
                computer[i] = true;
            }
            computerOccupati = NUM_COMPUTER;
            
            System.out.println("Professore " + idProfessore + " ha ottenuto accesso esclusivo al laboratorio");
            stampaStatoLaboratorio();
            
        } finally {
            lock.unlock();
        }
    }
    
    public void liberaAccessoProfessore(int idProfessore) {
        lock.lock();
        try {
            // Libera tutto il laboratorio
            professorePresente = false;
            for (int i = 1; i <= NUM_COMPUTER; i++) {
                computer[i] = false;
            }
            computerOccupati = 0;
            
            System.out.println("Professore " + idProfessore + " ha liberato il laboratorio");
            stampaStatoLaboratorio();
            
            // Sveglia tutti in attesa con priorità
            svegliaUtentiConPriorita();
            
        } finally {
            lock.unlock();
        }
    }
    
    public void richiestaAccessoTesista(int idTesista, int computerRichiesto) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Tesista " + idTesista + " richiede accesso al computer " + computerRichiesto);
            
            tesistiInAttesa++;
            
            // Attende finché non può accedere al computer specifico
            while (!possoAccedereTesista(computerRichiesto)) {
                codaTesisti.await();
            }
            
            tesistiInAttesa--;
            
            // Occupa il computer specifico
            computer[computerRichiesto] = true;
            computerOccupati++;
            
            System.out.println("Tesista " + idTesista + " ha ottenuto accesso al computer " + computerRichiesto);
            stampaStatoLaboratorio();
            
        } finally {
            lock.unlock();
        }
    }
    
    public void liberaAccessoTesista(int idTesista, int computerUsato) {
        lock.lock();
        try {
            // Libera il computer specifico
            computer[computerUsato] = false;
            computerOccupati--;
            
            System.out.println("Tesista " + idTesista + " ha liberato il computer " + computerUsato);
            stampaStatoLaboratorio();
            
            // Sveglia tutti in attesa con priorità
            svegliaUtentiConPriorita();
            
        } finally {
            lock.unlock();
        }
    }
    
    public int richiestaAccessoStudente(int idStudente) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Studente " + idStudente + " richiede accesso a un qualsiasi computer");
            
            studentiInAttesa++;
            
            // Attende finché non può accedere a un computer qualsiasi
            while (!possoAccedereStudente()) {
                codaStudenti.await();
            }
            
            studentiInAttesa--;
            
            // Trova e occupa il primo computer libero
            int computerAssegnato = trovaComputerLibero();
            computer[computerAssegnato] = true;
            computerOccupati++;
            
            System.out.println("Studente " + idStudente + " ha ottenuto accesso al computer " + computerAssegnato);
            stampaStatoLaboratorio();
            
            return computerAssegnato;
            
        } finally {
            lock.unlock();
        }
    }
    
    public void liberaAccessoStudente(int idStudente, int computerUsato) {
        lock.lock();
        try {
            // Libera il computer
            computer[computerUsato] = false;
            computerOccupati--;
            
            System.out.println("Studente " + idStudente + " ha liberato il computer " + computerUsato);
            stampaStatoLaboratorio();
            
            // Sveglia tutti in attesa con priorità
            svegliaUtentiConPriorita();
            
        } finally {
            lock.unlock();
        }
    }
    
    private boolean possoAccedereProfessore() {
        // Il professore può accedere solo se il laboratorio è completamente vuoto
        return computerOccupati == 0;
    }
    
    private boolean possoAccedereTesista(int computerRichiesto) {
        // Il tesista non può accedere se:
        // 1. C'è un professore presente
        // 2. Il computer specifico è già occupato
        // 3. Ci sono professori in attesa (priorità)
        return !professorePresente && 
               !computer[computerRichiesto] && 
               professoriInAttesa == 0;
    }
    
    private boolean possoAccedereStudente() {
        // Lo studente non può accedere se:
        // 1. C'è un professore presente
        // 2. Non ci sono computer liberi
        // 3. Ci sono professori o tesisti in attesa (priorità)
        return !professorePresente && 
               computerOccupati < NUM_COMPUTER && 
               professoriInAttesa == 0 && 
               tesistiInAttesa == 0;
    }
    
    private int trovaComputerLibero() {
        for (int i = 1; i <= NUM_COMPUTER; i++) {
            if (!computer[i]) {
                return i;
            }
        }
        return -1; // Non dovrebbe mai succedere se possoAccedereStudente() è corretto
    }
    
    private void svegliaUtentiConPriorita() {
        // Sveglia con priorità: prima professori, poi tesisti, poi studenti
        if (professoriInAttesa > 0) {
            codaProfessori.signalAll();
        } else if (tesistiInAttesa > 0) {
            codaTesisti.signalAll();
        } else if (studentiInAttesa > 0) {
            codaStudenti.signalAll();
        }
    }
    
    public void stampaStatoLaboratorio() {
        System.out.println("--- STATO LABORATORIO ---");
        System.out.println("Professore presente: " + (professorePresente ? "Sì" : "No"));
        System.out.println("Computer occupati: " + computerOccupati + "/" + NUM_COMPUTER);
        
        System.out.print("Computer liberi: ");
        for (int i = 1; i <= NUM_COMPUTER; i++) {
            if (!computer[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        
        System.out.print("Computer occupati: ");
        for (int i = 1; i <= NUM_COMPUTER; i++) {
            if (computer[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        
        System.out.println("In attesa - Professori: " + professoriInAttesa + 
                         ", Tesisti: " + tesistiInAttesa + 
                         ", Studenti: " + studentiInAttesa);
        System.out.println("------------------------");
    }
    
    public boolean isComputerOccupato(int numeroComputer) {
        lock.lock();
        try {
            return computer[numeroComputer];
        } finally {
            lock.unlock();
        }
    }
    
    public int getComputerOccupati() {
        lock.lock();
        try {
            return computerOccupati;
        } finally {
            lock.unlock();
        }
    }
}