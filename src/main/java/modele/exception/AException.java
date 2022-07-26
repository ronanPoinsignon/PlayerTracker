package modele.exception;

import service.DictionnaireService;
import service.ServiceManager;

public abstract class AException extends Exception implements IException {

	private static final long serialVersionUID = 8480634588766705867L;

	protected DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);

	protected AException() {

	}

	protected AException(final String msg) {
		super(msg);
	}

}