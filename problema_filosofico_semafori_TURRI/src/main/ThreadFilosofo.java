package main;
public class ThreadFilosofo {
	
	
	
	while (true) {
        try {
            // Pensa
            System.out.println("Il filosofo " + filosofo + " sta pensando.");
            Thread.sleep(1000);

            // Mangia
            int forchettaSx = filosofo;
            int forchettaDx = (filosofo + 1) % numFilosofi; // Forchetta del vicino di destra
            forchette[forchettaSx].acquire();
            System.out.println("Il filosofo " + filosofo + " ha preso la forchetta sinistra.");
            forchette[forchettaDx].acquire();
            System.out.println("Il filosofo " + filosofo + " ha preso la forchetta destra e sta mangiando.");
            Thread.sleep(1000);

            // Rilascia le forchette
            forchette[forchettaSx].release();
            forchette[forchettaDx].release();
            System.out.println("Il filosofo " + filosofo + " ha finito di mangiare.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
