package model;

import model.exception.PesciNonDisponibiliException;

public class Pescatore extends Thread {
	private Laghetto l;
	
	public Pescatore(Laghetto l, String nome) {
		setName(nome);
		this.l=l;
	}
	
	@Override
	public void run() {
		try {
			l.inizia(0);
		} catch (PesciNonDisponibiliException  e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e){
			System.out.println(e.getMessage());
		} finally { 
			try {
				l.finisci(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
