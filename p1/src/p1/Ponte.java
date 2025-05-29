package p1;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Ponte {
    private final int MAX_CAPACITA;
    private final ReentrantLock lock;
    
    // Condition variables
    private final Condition codaMagriSinistra;
    private final Condition codaMagriDestra;
    private final Condition codaGrassiSinistra;
    private final Condition codaGrassiDestra;
    
    // Contatori per persone sul ponte
    private int magriSulPonte;
    private int grassiSulPonte;
    
    // Direzione corrente (-1: sinistra, 0: nessuna, 1: destra)
    private int direzioneCorrente;
    
    // Contatori per direzione
    private int personeSinistra; // magri che vanno verso sinistra
    private int personeDestra;   // magri che vanno verso destra
    
    public Ponte(int maxCapacita) {
        this.MAX_CAPACITA = maxCapacita;
        this.lock = new ReentrantLock();
        
        this.codaMagriSinistra = lock.newCondition();
        this.codaMagriDestra = lock.newCondition();
        this.codaGrassiSinistra = lock.newCondition();
        this.codaGrassiDestra = lock.newCondition();
        
        this.magriSulPonte = 0;
        this.grassiSulPonte = 0;
        this.direzioneCorrente = 0; // nessuna direzione
        this.personeSinistra = 0;
        this.personeDestra = 0;
    }
    
    public void entraMagro(int direzione, int idUtente) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Utente magro " + idUtente + " vuole entrare verso " + 
                             (direzione == -1 ? "sinistra" : "destra"));
            
            Condition codaAttesa = (direzione == -1) ? codaMagriSinistra : codaMagriDestra;
            
            // Attesa finché non si può entrare
            while (!possoEntrareUtenteMagro(direzione)) {
                codaAttesa.await();
            }
            
            // Entrata sul ponte
            magriSulPonte++;
            if (direzione == -1) {
                personeSinistra++;
            } else {
                personeDestra++;
            }
            
            // Aggiorna direzione se è la prima persona
            if (direzioneCorrente == 0) {
                direzioneCorrente = direzione;
            }
            
            System.out.println("Utente magro " + idUtente + " è entrato sul ponte. " +
                             "Magri sul ponte: " + magriSulPonte + 
                             ", Direzione: " + (direzioneCorrente == -1 ? "sinistra" : "destra"));
            
        } finally {
            lock.unlock();
        }
    }
    
    public void esceMagro(int direzione, int idUtente) {
        lock.lock();
        try {
            magriSulPonte--;
            if (direzione == -1) {
                personeSinistra--;
            } else {
                personeDestra--;
            }
            
            System.out.println("Utente magro " + idUtente + " è uscito dal ponte. " +
                             "Magri rimasti: " + magriSulPonte);
            
            // Se non ci sono più persone, resetta la direzione
            if (magriSulPonte == 0 && grassiSulPonte == 0) {
                direzioneCorrente = 0;
            }
            
            // Sveglia tutti in attesa
            svegliaInAttesa();
            
        } finally {
            lock.unlock();
        }
    }
    
    public void entraGrasso(int direzione, int idUtente) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Utente grasso " + idUtente + " vuole entrare verso " + 
                             (direzione == -1 ? "sinistra" : "destra"));
            
            Condition codaAttesa = (direzione == -1) ? codaGrassiSinistra : codaGrassiDestra;
            
            // Attesa finché non si può entrare
            while (!possoEntrareUtenteGrasso(direzione)) {
                codaAttesa.await();
            }
            
            // Entrata sul ponte
            grassiSulPonte++;
            direzioneCorrente = direzione;
            
            System.out.println("Utente grasso " + idUtente + " è entrato sul ponte. " +
                             "Grassi sul ponte: " + grassiSulPonte + 
                             ", Direzione: " + (direzioneCorrente == -1 ? "sinistra" : "destra"));
            
        } finally {
            lock.unlock();
        }
    }
    
    public void esceGrasso(int direzione, int idUtente) {
        lock.lock();
        try {
            grassiSulPonte--;
            
            System.out.println("Utente grasso " + idUtente + " è uscito dal ponte. " +
                             "Grassi rimasti: " + grassiSulPonte);
            
            // Se non ci sono più persone, resetta la direzione
            if (magriSulPonte == 0 && grassiSulPonte == 0) {
                direzioneCorrente = 0;
            }
            
            // Sveglia tutti in attesa
            svegliaInAttesa();
            
        } finally {
            lock.unlock();
        }
    }
    
    private boolean possoEntrareUtenteMagro(int direzione) {
        // Non può entrare se c'è un grasso sul ponte
        if (grassiSulPonte > 0) {
            return false;
        }
        
        // Non può entrare se si supera la capacità massima
        if (magriSulPonte >= MAX_CAPACITA) {
            return false;
        }
        
        // Se il ponte è vuoto, può entrare
        if (direzioneCorrente == 0) {
            return true;
        }
        
        // Può entrare solo se la direzione è compatibile
        return direzioneCorrente == direzione;
    }
    
    private boolean possoEntrareUtenteGrasso(int direzione) {
        // Il grasso può entrare solo se il ponte è completamente vuoto
        return (magriSulPonte == 0 && grassiSulPonte == 0);
    }
    
    private void svegliaInAttesa() {
        // Sveglia tutti i thread in attesa
        codaMagriSinistra.signalAll();
        codaMagriDestra.signalAll();
        codaGrassiSinistra.signalAll();
        codaGrassiDestra.signalAll();
    }
    
    public void stampaStato() {
        lock.lock();
        try {
            System.out.println("=== STATO PONTE ===");
            System.out.println("Magri sul ponte: " + magriSulPonte);
            System.out.println("Grassi sul ponte: " + grassiSulPonte);
            System.out.println("Direzione corrente: " + 
                             (direzioneCorrente == -1 ? "sinistra" : 
                              direzioneCorrente == 1 ? "destra" : "nessuna"));
            System.out.println("Persone verso sinistra: " + personeSinistra);
            System.out.println("Persone verso destra: " + personeDestra);
            System.out.println("==================");
        } finally {
            lock.unlock();
        }
    }
}