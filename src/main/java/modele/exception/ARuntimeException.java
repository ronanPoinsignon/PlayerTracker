package modele.exception;

import service.DictionnaireService;
import service.ServiceManager;

public abstract class ARuntimeException extends RuntimeException implements IException {

	private static final long serialVersionUID = 3901621859288323690L;

	protected final transient DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);

	protected ARuntimeException() {

	}

	protected ARuntimeException(final String msg) {
		super(msg);
	}
}