package main;
import model.*;

public class Main {
	
    public static void main(String[] args) {
    	
        int maxSciatori = 5;
        int minSciatoriValle = 2;
        Seggiovia seggiovia = new Seggiovia(maxSciatori, minSciatoriValle);
        int numSciatori = 7;
        int nGiri = 3;

        while(true) {
        	//genera gli sciatori che aspettano la cabina
            for (int i=1; i<=numSciatori; i++) {
                String nomeSciatore = ("Sciatore "+i);
                new Thread(new Sciatore(nomeSciatore, seggiovia, nGiri)).start();
            }

            //nuovo thread cabina
            new Thread(new Cabina(seggiovia, nGiri)).start();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }
    
}