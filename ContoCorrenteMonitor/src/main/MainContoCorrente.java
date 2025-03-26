package main;

import java.util.concurrent.ThreadLocalRandom;

import model.ContoCorrente;
import model.Prelievo;
import model.Versamento;

public class MainContoCorrente {

	public static void main(String[] args) {
		
		ContoCorrente cc = new ContoCorrente(100);
		
		while(true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
			Versamento v = new Versamento(cc, getRandomImporto());
			Prelievo p = new Prelievo(cc, getRandomImporto());
			v.start();
			p.start();
		}
		
	}

	static int getRandomImporto() {
		return ThreadLocalRandom.current().nextInt(-100-1, 100+1);
	}
	
}
