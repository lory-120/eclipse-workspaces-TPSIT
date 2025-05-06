package model;

public class Seggiovia {
	
	//attributi
    private final int MAX_SCIATORI;
    private final int MIN_SCIATORI_VALLE;
    private boolean cabinaValle = true;
    private int sciatoriInCabina = 0;
    private int sciatoriInAttesaValle = 0;
    private int sciatoriInAttesaMonte = 0;
    private final Object lock = new Object();

    //metodo costruttore
    public Seggiovia(int maxSciatori, int minSciatoriValle) {
        this.MAX_SCIATORI = maxSciatori;
        this.MIN_SCIATORI_VALLE = minSciatoriValle;
    }

    public void imbarcaAValle(String nome) throws InterruptedException {
        synchronized(lock) {
            sciatoriInAttesaValle++;
            System.out.println(nome + " è in attesa a valle.");
            
            /*
             * la cabina imbarca da valle se:
             * - la cabina è a valle
             * - c'è il numero minimo di sciatori dentro la cabina
             * - la cabina non contiene più sciatori di quanti ne può contenere (<= MAX_SCIATORI)
             */
            while (!(cabinaValle && sciatoriInCabina < MAX_SCIATORI &&
                    (sciatoriInCabina < MIN_SCIATORI_VALLE || sciatoriInAttesaValle >= MIN_SCIATORI_VALLE))) {
                lock.wait();
            }
            
            sciatoriInCabina++;
            sciatoriInAttesaValle--;
            System.out.println(nome + " è salito a valle. (sciatori in cabina: " + sciatoriInCabina + "/" + MAX_SCIATORI + ")");
        }
    }

    public void sbarcaAMonte(String nome) throws InterruptedException {
        synchronized(lock) {
        	
        	//se la cabina è a valle, fai niente
            while(cabinaValle) {
                lock.wait();
            }
            
            sciatoriInCabina--;
            System.out.println(nome + " è sceso dal monte. (" + sciatoriInCabina + "/" + MAX_SCIATORI + ")");
        }
    }

    public void imbarcaAMonte(String nome) throws InterruptedException {
        synchronized(lock) {
            sciatoriInAttesaMonte++;
            System.out.println(nome + " è in attesa a monte.");
            
            /*
             * la cabina imbarca dal monte se:
             * - non è a valle
             * - non è piena
             */
            while(cabinaValle || sciatoriInCabina >= MAX_SCIATORI) {
                lock.wait();
            }
            
            sciatoriInCabina++;
            sciatoriInAttesaMonte--;
            System.out.println(nome + " è salito sul monte. (sciatori in cabina: " + sciatoriInCabina + "/" + MAX_SCIATORI + ")");
        }
    }

    public void sbarcaAValle(String nome) throws InterruptedException {
        synchronized(lock) {
        	
        	//se la cabina non è a valle, fai niente
            while(!cabinaValle) {
                lock.wait();
            }
            sciatoriInCabina--;
            System.out.println(nome + " è sceso a valle. (" + sciatoriInCabina + "/" + MAX_SCIATORI + ")");
        }
    }

    public void partiDaValle() throws InterruptedException {
        synchronized(lock) {
        	
        	/*
             * la cabina può partire da valle se:
             * - la cabina è a valle
             * - la cabina non ha più sciatori di quanti ne può contenere (< MAX_SCIATORI)
             * - la cabina ha il numero minimo di sciatori per partire da valle
             */
            while(!cabinaValle || sciatoriInCabina < MIN_SCIATORI_VALLE || sciatoriInCabina > MAX_SCIATORI) {
                lock.wait();
            }
            System.out.println("*****************La cabina e' in partenza dalla valle!*****************");
            cabinaValle = false;
            
            //sveglia gli altri thread, così che quelli che fanno sbarcare a monte si risveglino
            lock.notifyAll();
        }
    }

    public void partiDaMonte() throws InterruptedException {
        synchronized(lock) {
        	
        	/*
             * la cabina può partire da monte se:
             * - la cabina non è a valle
             * - la cabina non ha più sciatori di quanti ne può contenere
             */
            while(cabinaValle || sciatoriInCabina < 1 || sciatoriInCabina > MAX_SCIATORI) {
                lock.wait();
            }
            System.out.println("*****************La cabina è in partenza dal monte!*****************");
            cabinaValle = true;
            //sveglia gli altri thread, così che quelli che fanno sbarcare a valle si risveglino
            lock.notifyAll();
        }
    }
}
