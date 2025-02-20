/*
 * Progettare un’applicazione in cui un thread produttore produce un numero che si incrementa progressivamente
 * e che viene posizionato in un buffer d'appoggio, un pool di 10 thread prova a prelevare il numero prodotto
 * dal buffer e se ci riesce lo azzera, se lo trova vuoto non preleva. Il thread che riesce a prelevare ne fa
 * il quadrato e lo visualizza.
 * 
 * CLASSI:
 * - produttore
 * - buffer
 * - consumatore
*/

package main;

import model.*;

public class MainQuadratoNumeriSemafori {

	public static void main(String args[]) {
		
		Buffer buffer = new Buffer();
		
		Produttore prod = new Produttore(buffer, 3000);
		prod.start();
		
		Consumatore[] cons = new Consumatore[10];
		
		for(int i=0; i<10; i++) {
			cons[i] = new Consumatore(buffer, 500+i*10, i);
			cons[i].start();
		}
		
	}
	
}
