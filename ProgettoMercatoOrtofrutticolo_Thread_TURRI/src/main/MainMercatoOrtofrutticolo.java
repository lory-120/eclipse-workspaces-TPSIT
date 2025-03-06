package main;

//import java.util.concurrent.ThreadLocalRandom;
import model.*;

public class MainMercatoOrtofrutticolo {

	public static void main(String args[]) {
		
		int nGrossisti = 10;
		int nProduttori = 5;
		int prezzoVendita = 200;
		int prezzoAcquisto = 100;
		int maxCapaxityQuintali = 40;
		
		Mercato mercato = new Mercato(maxCapaxityQuintali, prezzoVendita, prezzoAcquisto);
		
		for(int i=0; i<nProduttori; i++) {
			Produttore p = new Produttore(mercato, i+1);
			p.start();
		}
		
		for(int i=0; i<nGrossisti; i++) {
			Grossista g = new Grossista(mercato, i+1);
			g.start();
		}
		
		
		/*
		(stavo testando come generare un numero random compreso tra due numeri) 
		
		int tmp = 0;
		while(tmp != 10) {
			tmp = ThreadLocalRandom.current().nextInt(1, 10);
			System.out.println(Integer.toString(tmp));
		}
		System.out.println("trovatooooooooooooo");
		*/
		
	}
	
}
