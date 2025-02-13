package main;
import java.util.concurrent.Semaphore;
/**
 * filosofi: n
 * posate: n
 * semafori (oggetti di classe Semaphore): n
 * 
 * ogni filosofo ha una posata
 * ogni filosofo ha fame e, per mangiare, ha bisogno di 2 posate
 * ogni filosofo è un thread
 * ogni semaforo deve gestire 2 thread
 * 
 * IL MAIN DEVE FARE IL - POSSIBILE (quasi nulla)
 */

public class MainSemaforoFilosofi {
	
	/*
	Filosofo[] filosofi = new Filosofo[5];
	
	for(Filosofo f:filosofi) {
		f.setStato(StatoFilosofo.PENSA);
	}
	*/	

    public static void main(String[] args) {
        int numFilosofi = 5;
        int filosofo;
        Semaphore[] forchette = new Semaphore[numFilosofi];

        // Inizializza le forchette (un semaforo per ogni forchetta)
        for (int i = 0; i < numFilosofi; i++) {
            forchette[i] = new Semaphore(1);
        }

        // Crea i thread per i filosofi
        for (int i = 0; i < numFilosofi; i++) {
            filosofo = i;
            new Thread(() -> {
                
            }).start();
        }
	}  
	
}