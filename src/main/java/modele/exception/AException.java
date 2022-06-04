package modele.exception;

public abstract class AException extends Exception implements IException {

	private static final long serialVersionUID = 8480634588766705867L;

	protected AException(final String msg) {
		super(msg);
	}
}