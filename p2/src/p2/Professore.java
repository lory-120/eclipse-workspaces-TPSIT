package p2;
import java.util.Random;

public class Professore extends Thread {
    private final int idProfessore;
    private final TutorLaboratorio tutor;
    private final Random random;
    
    public Professore(int idProfessore, TutorLaboratorio tutor) {
        this.idProfessore = idProfessore;
        this.tutor = tutor;
        this.random = new Random();
        this.setName("Prof-" + idProfessore);
    }
    
    @Override
    public void run() {
        try {
            // Simula tempo di arrivo al laboratorio
            Thread.sleep(random.nextInt(2000) + 1000);
            
            // Richiede accesso esclusivo al laboratorio
            tutor.richiestaAccessoProfessore(idProfessore);
            
            // Simula utilizzo del laboratorio per prove in rete
            int tempoUtilizzo = random.nextInt(4000) + 3000; // 3-7 secondi
            System.out.println("Professore " + idProfessore + 
                             " sta eseguendo prove in rete su tutti i computer (tempo: " + 
                             tempoUtilizzo + "ms)");
            Thread.sleep(tempoUtilizzo);
            
            // Libera il laboratorio
            tutor.liberaAccessoProfessore(idProfessore);
            
            System.out.println("Professore " + idProfessore + " ha completato le prove in rete");
            
        } catch (InterruptedException e) {
            System.out.println("Professore " + idProfessore + " è stato interrotto");
            Thread.currentThread().interrupt();
        }
    }
    
    public int getIdProfessore() {
        return idProfessore;
    }
}