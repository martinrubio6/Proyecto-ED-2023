package excepciones;

public class BoundaryViolationException extends Exception {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoundaryViolationException(String msj) {
		super(msj);
	}
}
