package p1;
import java.util.Random;

public class UtenteGrasso extends Thread {
    private final int idUtente;
    private final Ponte ponte;
    private final int direzione; // -1 per sinistra, 1 per destra
    private final Random random;
    
    public UtenteGrasso(int idUtente, Ponte ponte, int direzione) {
        this.idUtente = idUtente;
        this.ponte = ponte;
        this.direzione = direzione;
        this.random = new Random();
        this.setName("UtenteG-" + idUtente);
    }
    
    @Override
    public void run() {
        try {
            // Simula tempo di arrivo al ponte
            Thread.sleep(random.nextInt(1500) + 800);
            
            // Entra sul ponte
            ponte.entraGrasso(direzione, idUtente);
            
            // Simula tempo di attraversamento (più lungo per gli utenti grassi)
            int tempoAttraversamento = random.nextInt(3000) + 2000;
            System.out.println("Utente grasso " + idUtente + 
                             " sta attraversando il ponte (tempo: " + tempoAttraversamento + "ms)");
            Thread.sleep(tempoAttraversamento);
            
            // Esce dal ponte
            ponte.esceGrasso(direzione, idUtente);
            
            System.out.println("Utente grasso " + idUtente + " ha completato l'attraversamento");
            
        } catch (InterruptedException e) {
            System.out.println("Utente grasso " + idUtente + " è stato interrotto");
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