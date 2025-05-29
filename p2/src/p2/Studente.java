package p2;
import java.util.Random;

public class Studente extends Thread {
    private final int idStudente;
    private final TutorLaboratorio tutor;
    private final Random random;
    private int computerAssegnato;
    
    public Studente(int idStudente, TutorLaboratorio tutor) {
        this.idStudente = idStudente;
        this.tutor = tutor;
        this.random = new Random();
        this.computerAssegnato = -1;
        this.setName("Studente-" + idStudente);
    }
    
    @Override
    public void run() {
        try {
            // Simula tempo di arrivo al laboratorio
            Thread.sleep(random.nextInt(1000) + 500);
            
            // Richiede accesso a un qualsiasi computer
            computerAssegnato = tutor.richiestaAccessoStudente(idStudente);
            
            // Simula studio/esercitazioni
            int tempoStudio = random.nextInt(2500) + 1500; // 1.5-4 secondi
            System.out.println("Studente " + idStudente + 
                             " sta studiando/facendo esercitazioni sul computer " + 
                             computerAssegnato + " (tempo: " + tempoStudio + "ms)");
            Thread.sleep(tempoStudio);
            
            // Libera il computer
            tutor.liberaAccessoStudente(idStudente, computerAssegnato);
            
            System.out.println("Studente " + idStudente + " ha completato lo studio");
            
        } catch (InterruptedException e) {
            System.out.println("Studente " + idStudente + " è stato interrotto");
            Thread.currentThread().interrupt();
        }
    }
    
    public int getIdStudente() {
        return idStudente;
    }
    
    public int getComputerAssegnato() {
        return computerAssegnato;
    }
}