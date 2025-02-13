package model;

import model.exception.OperazioneNonConsentitaException;

public class Versamento extends Operazione{
	
	public Versamento (ContoCorrente cc, int numOperazione, double importo) {
		super(cc, numOperazione, importo);
	}
	
	@Override
	public void run() {
		double saldo=0;
		try {
			saldo=this.getCc().versamento(this.getImporto());
			System.out.println(Thread.currentThread().getName()+" ha versato "+ this.getImporto()+" €. Saldo disponibile: "+saldo);
		} catch (OperazioneNonConsentitaException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+super.toString();
	}
}
