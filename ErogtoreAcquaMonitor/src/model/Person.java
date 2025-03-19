package model;


import java.util.Random;

public class Person extends Thread {
    private final DistributoreMonitor distributore;
    private final String waterType;
    private final String waterTemp;
    private final boolean isWheelchair;
    private final Random random = new Random();
    
    public Person(DistributoreMonitor distributore, 
    		String waterType, String waterTemp, boolean isWheelchair) {
        this.distributore = distributore;
        this.waterType = waterType;
        this.waterTemp = waterTemp;
        this.isWheelchair = isWheelchair;
    }
    
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + 
            		(isWheelchair ? " in carrozzina " : " in piedi ") +
            		" vuole bere acqua " + waterType + " e " + waterTemp);

            distributore.serveWater(waterType, waterTemp, isWheelchair);
            // 
            Thread.sleep(random.nextInt(1500) + 100);
            distributore.releaseWater(waterType, waterTemp, isWheelchair);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
