package p2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulazioneLaboratorio {
    public static void main(String[] args) {
        // Configurazione della simulazione
        final int NUM_PROFESSORI = 2;
        final int NUM_TESISTI = 5;
        final int NUM_STUDENTI = 10;
        
        // Crea il tutor del laboratorio
        TutorLaboratorio tutor = new TutorLaboratorio();
        
        // Lista per tenere traccia di tutti i thread
        List<Thread> tuttiGliUtenti = new ArrayList<>();
        Random random = new Random();
        
        System.out.println("=== INIZIO SIMULAZIONE LABORATORIO S02 BUZZI ===");
        System.out.println("Computer disponibili: 20 (numerati da 1 a 20)");
        System.out.println("Professori: " + NUM_PROFESSORI + " (accesso esclusivo a tutto il lab)");
        System.out.println("Tesisti: " + NUM_TESISTI + " (accesso esclusivo a computer specifico)");
        System.out.println("Studenti: " + NUM_STUDENTI + " (accesso a qualsiasi computer)");
        System.out.println("Priorità: Professori > Tesisti > Studenti");
        System.out.println("===============================================\n");
        
        // Crea e avvia professori
        for (int i = 1; i <= NUM_PROFESSORI; i++) {
            Professore professore = new Professore(i, tutor);
            tuttiGliUtenti.add(professore);
            professore.start();
        }
        
        // Crea e avvia tesisti con computer specifici
        for (int i = 1; i <= NUM_TESISTI; i++) {
            // Assegna un computer casuale tra 1 e 20 per ogni tesista
            int computerSpecifico = random.nextInt(20) + 1;
            Tesista tesista = new Tesista(i, computerSpecifico, tutor);
            tuttiGliUtenti.add(tesista);
            tesista.start();
        }
        
        // Crea e avvia studenti
        for (int i = 1; i <= NUM_STUDENTI; i++) {
            Studente studente = new Studente(i, tutor);
            tuttiGliUtenti.add(studente);
            studente.start();
        }
        
        // Thread per monitorare lo stato del laboratorio
        Thread monitorLaboratorio = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(4000); // Stampa stato ogni 4 secondi
                    System.out.println("\n=== MONITORAGGIO PERIODICO ===");
                    tutor.stampaStatoLaboratorio();
                    System.out.println("==============================\n");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        monitorLaboratorio.setDaemon(true);
        monitorLaboratorio.start();
        
        // Thread per statistiche aggiuntive
        Thread statistiche = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(6000);
                    System.out.println("\n>>> Utilizzo laboratorio: " + 
                                     tutor.getComputerOccupati() + "/20 computer in uso");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        statistiche.setDaemon(true);
        statistiche.start();
        
        // Aspetta che tutti gli utenti completino il loro lavoro
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
        tutor.stampaStatoLaboratorio();
        System.out.println("Tutti gli utenti hanno completato il loro lavoro nel laboratorio!");
        System.out.println("Il laboratorio è ora completamente libero e pronto per nuovi utenti.");
    }
    
    /**
     * Metodo di utilità per eseguire test specifici
     */
    public static void eseguiTestPriorita() {
        System.out.println("\n=== TEST PRIORITÀ ===");
        TutorLaboratorio tutor = new TutorLaboratorio();
        
        // Simula scenario con tutti i tipi di utenti contemporaneamente
        Thread prof = new Professore(99, tutor);
        Thread tesista = new Tesista(99, 10, tutor);
        Thread studente = new Studente(99, tutor);
        
        // Avvia in ordine inverso di priorità per testare il sistema
        studente.start();
        tesista.start();
        prof.start();
        
        try {
            prof.join();
            tesista.join();
            studente.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Test priorità completato!");
    }
}