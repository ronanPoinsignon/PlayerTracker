package appli.exception;

import modele.exception.ARuntimeException;
import service.ServiceManager;
import service.TrayIconService;

public class BadOsException extends ARuntimeException {

	private static final long serialVersionUID = 5436675013904176145L;
	private static final String MESSAGE = "Impossible de lancer l'application";

	private final TrayIconService ts = ServiceManager.getInstance(TrayIconService.class);

	public BadOsException() {
		super(BadOsException.MESSAGE);
	}

	@Override
	public Runnable next() {
		return () -> {
			ts.quitter();
		};
	}

	@Override
	public String getDescription() {
		return dictionnaire.getText("BadOsExceptionDescription").getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getText("BadOsExceptionMessage").getValue();
	}
}
