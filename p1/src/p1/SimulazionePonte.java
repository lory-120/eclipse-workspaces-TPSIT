package p1;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulazionePonte {
    public static void main(String[] args) {
        // Configurazione della simulazione
        final int MAX_CAPACITA_PONTE = 3;
        final int NUM_UTENTI_MAGRI = 8;
        final int NUM_UTENTI_GRASSI = 4;
        
        // Crea il ponte
        Ponte ponte = new Ponte(MAX_CAPACITA_PONTE);
        
        // Lista per tenere traccia di tutti i thread
        List<Thread> tuttiGliUtenti = new ArrayList<>();
        Random random = new Random();
        
        System.out.println("=== INIZIO SIMULAZIONE PONTE ===");
        System.out.println("Capacità massima ponte: " + MAX_CAPACITA_PONTE);
        System.out.println("Utenti magri: " + NUM_UTENTI_MAGRI);
        System.out.println("Utenti grassi: " + NUM_UTENTI_GRASSI);
        System.out.println("Direzioni: -1 = sinistra, 1 = destra");
        System.out.println("=====================================\n");
        
        // Crea e avvia utenti magri
        for (int i = 1; i <= NUM_UTENTI_MAGRI; i++) {
            int direzione = random.nextBoolean() ? -1 : 1; // Direzione casuale
            UtenteMagro utenteMagro = new UtenteMagro(i, ponte, direzione);
            tuttiGliUtenti.add(utenteMagro);
            utenteMagro.start();
        }
        
        // Crea e avvia utenti grassi
        for (int i = 1; i <= NUM_UTENTI_GRASSI; i++) {
            int direzione = random.nextBoolean() ? -1 : 1; // Direzione casuale
            UtenteGrasso utenteGrasso = new UtenteGrasso(i, ponte, direzione);
            tuttiGliUtenti.add(utenteGrasso);
            utenteGrasso.start();
        }
        
        // Thread per monitorare lo stato del ponte
        Thread monitorPonte = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(3000); // Stampa stato ogni 3 secondi
                    ponte.stampaStato();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        monitorPonte.setDaemon(true);
        monitorPonte.start();
        
        // Aspetta che tutti gli utenti completino l'attraversamento
        for (Thread utente : tuttiGliUtenti) {
            try {
                utente.join();
            } catch (InterruptedException e) {
                System.err.println("Interruzione durante l'attesa del completamento degli utenti");
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Stampa stato finale
        System.out.println("\n=== SIMULAZIONE COMPLETATA ===");
        ponte.stampaStato();
        System.out.println("Tutti gli utenti hanno attraversato il ponte con successo!");
    }
}