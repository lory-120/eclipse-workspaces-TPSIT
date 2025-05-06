package model;
import java.util.concurrent.ThreadLocalRandom;

public class Cabina implements Runnable {
	//attributi
	private static final int MAX_WAIT_TIME = 2000;
	private static final int MIN_WAIT_TIME = 3000;
    private final Seggiovia seggiovia;
    private final int nGiri;

    //metodo costruttore
    public Cabina(Seggiovia seggiovia, int nGiri) {
        this.seggiovia = seggiovia;
        this.nGiri = nGiri;
    }

    @Override
    public void run() {
        try {
            for (int i=0; i<nGiri*2; i++) {
                seggiovia.partiDaValle();
                Thread.sleep(getRandomTime()); //simula il tempo di viaggio
                System.out.println("*****************Cabina arrivata a monte (giro " + (i+1) + ").*****************");
                Thread.sleep(1000); //simula il tempo di discesa al monte degli sciatori dalla cabina
                seggiovia.partiDaMonte();
                Thread.sleep(getRandomTime()); //simula il tempo di viaggio
                System.out.println("*****************Cabina arrivata a valle (giro " + (i+1) + ").*****************");
                Thread.sleep(1000); //simula il tempo di discesa a valle degli sciatori dalla cabina
            }
            System.out.println("La cabina ha terminato i suoi giri.");
        } catch (InterruptedException e) {
            System.out.println("Il thread della cabina è stato interrotto.");
            Thread.currentThread().interrupt();
        }
    }
    
    
    private static int getRandomTime() {
    	return ThreadLocalRandom.current().nextInt(MAX_WAIT_TIME, MIN_WAIT_TIME);
    }
    
}