package model;

public abstract class Operazione extends Thread {
	private double importo;
	private int numOperazione;
	private ContoCorrente cc;
	
	public Operazione (ContoCorrente cc, int numOperazione, double importo) {
		this.cc=cc;
		this.numOperazione=numOperazione;
		this.importo=importo;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public int getNumOperazione() {
		return numOperazione;
	}

	public void setNumOperazione(int numOperazione) {
		this.numOperazione = numOperazione;
	}

	public ContoCorrente getCc() {
		return cc;
	}

	public void setCc(ContoCorrente cc) {
		this.cc = cc;
	}
	
	public abstract void run();
	
	@Override
	public String toString() {
		return "Operazione [importo=" + importo + ", numOperazione=" + numOperazione + "]";
	}
	
	
}
