/**
 * 2 fasi per produrre un microprocessore:
 * - assemblaggio (dove si producono i microprocessori);
 * - inscatolamento (in scatole di capacità N).
 * 
 * C'è un thread dedicato per ognuna delle due fasi.
 * 
 * Vincoli:
 * - l'inscatolamento può essere attivato solo quando ci sono almeno N prodotti
 * 	(N era la capacità di una scatola).
 * - La fase di assemblaggio non si può fare se ci sono ancora MAX schede da
 * 	inscatolare (quindi si produce quando nProcessoriProdotti < MAX).
 * 
 * Deve esserci un grado buono di concorrenza tra thread.
 */

package main;

import model.*;

public class MainAziendaMicroprocessori {

	public static void main(String[] args) {
		/*
		Assemblaggio assemblaggio = new Assemblaggio();
		Inscatolamento inscatolamento = new Inscatolamento();
		
		assemblaggio.start();
		inscatolamento.start();
		*/
	}

}
