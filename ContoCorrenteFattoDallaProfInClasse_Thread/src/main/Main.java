package main;

import java.util.Random;

import model.*;

public class Main {

	public static void main(String[] args) {
		ContoCorrente cc=new ContoCorrente(3000);
		Random r=new Random();
		int i=0;
		
		while(true) {
			boolean isVersamento=r.nextBoolean();
			if(isVersamento) {
				Versamento v=new Versamento(cc, i++, r.nextDouble()*2000+1);
				v.start();
			} else {
				Prelievo p=new Prelievo(cc, i++, r.nextDouble()*2000+1);
				p.start();
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
