package model;

import model.exception.PesciNonDisponibiliException;

public class Addetto extends Thread {
	private Laghetto l;
	
	public Addetto(Laghetto l) {
		setName("Franco");
		this.l=l;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				l.inizia(1);
				
			} catch (PesciNonDisponibiliException e) {
				System.out.println(e.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally {
				try {
					l.finisci(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
