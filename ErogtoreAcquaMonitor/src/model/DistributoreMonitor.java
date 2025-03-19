package model;
import java.util.concurrent.locks.*;

public class DistributoreMonitor {

    private Lock lock = new ReentrantLock();
    private Condition noWheelchair;
    private Condition roomWaterAvailable;
    private Condition freshWaterAvailable;
    private Condition carbonMaintenance;

    private int carbonatedWaterCount = 0;
    private int plainWaterCount = 0;
    private boolean carbonationMaintenance = false;
    private boolean freshOccupied = false;
    private boolean roomOccupied = false;
    private boolean enteredWheelchair = false;   

    public DistributoreMonitor () {
    	lock.lock();
        noWheelchair = lock.newCondition();
        roomWaterAvailable = lock.newCondition();
        freshWaterAvailable = lock.newCondition();
        carbonMaintenance = lock.newCondition();
        lock.unlock();
    }
    
    public void serveWater(String waterType, String waterTemp, boolean isWheelchair) throws InterruptedException {
        lock.lock();
        try {       	
        	while (enteredWheelchair) 
        		// se c'è davanti una persona in carrozzina si attende 
        		noWheelchair.await();
        	if (isWheelchair) // persona in carrozzina
            {      	
            	if (waterType.equals("gassata")) 
            		while (carbonationMaintenance)
            			carbonMaintenance.await();
            	// la persona in carrozzina non ha altre carrozzine davanti
            	enteredWheelchair = true;

            	// superato eventuale stop per manutenzione
            	// deve avere entrambi i rubinetti liberi
            	while (freshOccupied)
            		freshWaterAvailable.await();
            	while (roomOccupied)
            		roomWaterAvailable.await();
            }
            else {  // persona in piedi      	 	
            	if (waterType.equals("gassata")) 
            		while (carbonationMaintenance)
            			carbonMaintenance.await(); 
            	//
            	while (enteredWheelchair) 
            		noWheelchair.await();
            	//
            	if (waterTemp.equals("ambiente")) {
            		while (roomOccupied)
            			roomWaterAvailable.await();
            		roomOccupied = true;            		
            	}
            	else if (waterTemp.equals("fredda")) {
            		while (freshOccupied)
            			freshWaterAvailable.await();
            		freshOccupied = true;            		
            	}
            	// else 
            }               	
//
            if (waterType.equals("gassata")) {
                carbonatedWaterCount++;
                if (carbonatedWaterCount >= 12) {
                    System.out.println("Inizio manutenzione programmata");                	
                    carbonationMaintenance = true;
                    carbonatedWaterCount = plainWaterCount = 0;
                }
            } else {
                plainWaterCount++;
            }
            
            System.out.println(Thread.currentThread().getName() + 
            		(isWheelchair ? " in carrozzina " : " in piedi ") +
            		" sta bevendo acqua " + waterType + " e " + waterTemp);
        } finally {
            lock.unlock();
        }
    }
    
    public void releaseWater(String waterType, String waterTemp, boolean isWheelchair) throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + 
            		(isWheelchair ? " in carrozzina " : " in piedi ") +
            		" ha finito di bere acqua " + waterType + " e " + waterTemp);
            if (carbonationMaintenance)
                System.out.println("manutenzione programmata " + plainWaterCount);                	
            if (carbonationMaintenance && plainWaterCount >= 5) {
                carbonationMaintenance = false;
                System.out.println("Fine manutenzione programmata");                	
                carbonatedWaterCount = 0;
                plainWaterCount = 0;
                carbonMaintenance.signalAll();
            }
            if (isWheelchair) {
            	roomOccupied = freshOccupied = false;
            	enteredWheelchair = false;
            	noWheelchair.signalAll(); 
                roomWaterAvailable.signalAll();
                freshWaterAvailable.signalAll();
            	
            }
            else {
            	if (waterTemp.equals("ambiente")) {
            		roomOccupied = false; 
                    roomWaterAvailable.signalAll();
            	}
            	else if (waterTemp.equals("fredda")) {
            		freshOccupied = false;
                    freshWaterAvailable.signalAll();
            	}            	
            }
        	
        } finally {
            lock.unlock();
        }
    }    
}
