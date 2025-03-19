package main;
import java.util.Random;

import model.DistributoreMonitor;
import model.Person;

public class ErogatoreAcqua {
    static String[] waterTypes = {"liscia", "gassata"};
    static String[] waterTemps = {"ambiente", "fredda"};
	
	    public static void main(String[] args) {
	        DistributoreMonitor distributore = new DistributoreMonitor();
	        
	        // Generate people in an infinite loop
	       
            Random random = new Random();
            while (true) {
                String waterType = waterTypes[random.nextInt(waterTypes.length)];
                String waterTemp = waterTemps[random.nextInt(waterTemps.length)];
                boolean isWheelchair = random.nextInt(6) > 4 ? true : false;
                Person person = new Person(distributore, waterType,
                								waterTemp, isWheelchair);
                person.start();
                
                try {
                    Thread.sleep(random.nextInt(1200) + 100); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
		}
}
