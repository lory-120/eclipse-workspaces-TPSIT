package model;

import utilities.OperazioneNonConsentitaException;

public class Versamento extends Operazione {

	public Versamento(ContoCorrente cc, int nOperazione, double importo) {
		super(cc, nOperazione, importo);
	}
	
	@Override
	public void run() {
		double saldo = 0;
		try {
			saldo = this.getCc().versamento(this.getImporto());
			System.out.println(Thread.currentThread().getName() + " ha prelevato " + this.getImporto() + "€. Nuovo saldo disponibile: " + saldo);
		} catch(OperazioneNonConsentitaException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + super.toString();
	}
	
}
