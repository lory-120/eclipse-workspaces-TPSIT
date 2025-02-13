package model;

public abstract class Operazione extends Thread {
	
	private double importo;
	private int nOperazione;
	private ContoCorrente cc;
	
	public Operazione(ContoCorrente cc, int nOperazione, double importo) {
		this.cc = cc;
		this.nOperazione = nOperazione;
		this.importo = importo;
	}

	
	//metodi get/set
	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public int getnOperazione() {
		return nOperazione;
	}

	public void setnOperazione(int nOperazione) {
		this.nOperazione = nOperazione;
	}

	public ContoCorrente getCc() {
		return cc;
	}

	public void setCc(ContoCorrente cc) {
		this.cc = cc;
	}	
	
}
