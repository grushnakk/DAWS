package ch.unibe.scg.dicto;

public class UnexpectedTokenException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnexpectedTokenException(String msg) {
		super(msg);
	}
	
	public UnexpectedTokenException() {
		super();
	}
}
