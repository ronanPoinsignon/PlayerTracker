package modele.exception;

public abstract class ARuntimeException extends RuntimeException implements IException {

	/**
	 *
	 */
	private static final long serialVersionUID = 3901621859288323690L;

	protected ARuntimeException(String msg) {
		super(msg);
	}
}
