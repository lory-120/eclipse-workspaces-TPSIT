package main;

import model.Assemblaggio;
import model.GestioneCatenaMontaggio;
import model.Inscatolamento;

public class MainCatenaDiMontaggio {
	
	public static void main(String args[]) {
		
		GestioneCatenaMontaggio catenaMontaggio = new GestioneCatenaMontaggio(5, 20);
		
		Assemblaggio ass1 = new Assemblaggio(catenaMontaggio);
		Assemblaggio ass2 = new Assemblaggio(catenaMontaggio);
		Inscatolamento ins = new Inscatolamento(catenaMontaggio);
		
		ass1.start();
		ass2.start();
		ins.start();
		
	}
	
}
