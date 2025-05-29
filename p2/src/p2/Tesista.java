package p2;
import java.util.Random;

public class Tesista extends Thread {
    private final int idTesista;
    private final int computerRichiesto;
    private final TutorLaboratorio tutor;
    private final Random random;
    
    public Tesista(int idTesista, int computerRichiesto, TutorLaboratorio tutor) {
        this.idTesista = idTesista;
        this.computerRichiesto = computerRichiesto;
        this.tutor = tutor;
        this.random = new Random();
        this.setName("Tesista-" + idTesista);
    }
    
    @Override
    public void run() {
        try {
            // Simula tempo di arrivo al laboratorio
            Thread.sleep(random.nextInt(1500) + 800);
            
            // Richiede accesso al computer specifico
            tutor.richiestaAccessoTesista(idTesista, computerRichiesto);
            
            // Simula sviluppo della tesi
            int tempoSviluppo = random.nextInt(3000) + 2000; // 2-5 secondi
            System.out.println("Tesista " + idTesista + 
                             " sta sviluppando la tesi sul computer " + computerRichiesto + 
                             " (tempo: " + tempoSviluppo + "ms)");
            Thread.sleep(tempoSviluppo);
            
            // Libera il computer
            tutor.liberaAccessoTesista(idTesista, computerRichiesto);
            
            System.out.println("Tesista " + idTesista + " ha completato il lavoro sulla tesi");
            
        } catch (InterruptedException e) {
            System.out.println("Tesista " + idTesista + " è stato interrotto");
            Thread.currentThread().interrupt();
        }
    }
    
    public int getIdTesista() {
        return idTesista;
    }
    
    public int getComputerRichiesto() {
        return computerRichiesto;
    }
}