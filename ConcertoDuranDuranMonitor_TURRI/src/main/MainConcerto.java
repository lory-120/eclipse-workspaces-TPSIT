/**
 * Per il concerto dei Duran Duran sono disponibili solo 100
   biglietti e vi è una cassa che effettua la vendita, ogni persona in fila potrà
   acquistare solo un biglietto, se però la persona fa parte del fan club avrà
   diritto all’acquisto di massimo 3 biglietti. Si implementi una soluzione in
   java facendo uso dei monitor per la sincronizzazione.
 */

package main;

import java.util.concurrent.ThreadLocalRandom;
import model.*;

public class MainConcerto {

	public static void main(String args[]) {
		
		//(non mi piacciono i duran duran)
		
		Cassa cassa = new Cassa("cassa bella");
		
		int i = 0;
		while(true) {
			i++;
			boolean isIscritto = ThreadLocalRandom.current().nextBoolean();
			Cliente c = new Cliente(("cliente "+i), isIscritto, cassa);
			
			c.start();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
}
