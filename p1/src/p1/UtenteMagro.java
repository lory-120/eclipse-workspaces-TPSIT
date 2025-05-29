package p1;
import java.util.Random;

public class UtenteMagro extends Thread {
    private final int idUtente;
    private final Ponte ponte;
    private final int direzione; // -1 per sinistra, 1 per destra
    private final Random random;
    
    public UtenteMagro(int idUtente, Ponte ponte, int direzione) {
        this.idUtente = idUtente;
        this.ponte = ponte;
        this.direzione = direzione;
        this.random = new Random();
        this.setName("UtenteM-" + idUtente);
    }
    
    @Override
    public void run() {
        try {
            // Simula tempo di arrivo al ponte
            Thread.sleep(random.nextInt(1000) + 500);
            
            // Entra sul ponte
            ponte.entraMagro(direzione, idUtente);
            
            // Simula tempo di attraversamento
            int tempoAttraversamento = random.nextInt(2000) + 1000;
            System.out.println("Utente magro " + idUtente + 
                             " sta attraversando il ponte (tempo: " + tempoAttraversamento + "ms)");
            Thread.sleep(tempoAttraversamento);
            
            // Esce dal ponte
            ponte.esceMagro(direzione, idUtente);
            
            System.out.println("Utente magro " + idUtente + " ha completato l'attraversamento");
            
        } catch (InterruptedException e) {
            System.out.println("Utente magro " + idUtente + " è stato interrotto");
            Thread.currentThread().interrupt();
        }
    }
    
    public int getIdUtente() {
        return idUtente;
    }
    
    public int getDirezione() {
        return direzione;
    }
}