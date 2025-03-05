package model.exception;

public class PesciNonDisponibiliException extends RuntimeException {
	public PesciNonDisponibiliException() {
		super("Pesci non disponibili per la pesca");
	}
	
	public PesciNonDisponibiliException(String msg) {
		super(msg);
	}

}
