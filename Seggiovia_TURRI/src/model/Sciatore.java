package model;

import java.util.concurrent.ThreadLocalRandom;

public class Sciatore implements Runnable {
	
	//attributi
	private static final int MAX_WAIT_TIME = 1000;
	private static final int MIN_WAIT_TIME = 3000;
    private final String nome;
    private final Seggiovia seggiovia;
    private final int nGiri;

    //metodo costruttore
    public Sciatore(String nome, Seggiovia seggiovia, int nGiri) {
        this.nome = nome;
        this.seggiovia = seggiovia;
        this.nGiri = nGiri;
    }

    @Override
    public void run() {
        try {
        	//simula che lo sciatore vuole andare nel monte dalla valle, sciare e tornare a valle per nGiri
            for (int j=0; j<nGiri; j++) {
                Thread.sleep(getRandomTime()); //simula il tempo dello sciatore che scia a valle
                System.out.println(nome + " arriva a valle (Giro " + (j + 1) + ").");
                seggiovia.imbarcaAValle(nome);
                seggiovia.sbarcaAMonte(nome);
                System.out.println(nome + " sta sciando (Giro " + (j + 1) + ").");
                Thread.sleep(getRandomTime()); //simula il tempo che lo sciatore si diverte a sciare in monte
                System.out.println(nome + " torna a monte (Giro " + (j + 1) + ").");
                seggiovia.imbarcaAMonte(nome);
                seggiovia.sbarcaAValle(nome);
                System.out.println(nome + " ha completato il giro " + (j + 1) + ".");
            }
            System.out.println(nome + " ha finito per oggi.");
        } catch (InterruptedException e) {
            System.out.println(nome + " è stato interrotto.");
            Thread.currentThread().interrupt();
        }
    }
    
    
    private static int getRandomTime() {
    	return ThreadLocalRandom.current().nextInt(MAX_WAIT_TIME, MIN_WAIT_TIME);
    }
    
}