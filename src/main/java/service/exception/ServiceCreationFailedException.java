package service.exception;

public class ServiceCreationFailedException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1537206221151886351L;

	public ServiceCreationFailedException() {
		super("Une exception a eu lieu.");
	}

	public ServiceCreationFailedException(String msg) {
		super(msg);
	}

	public ServiceCreationFailedException(Exception e) {
		super(e);
	}
}
