package appli.exception;

import modele.exception.ARuntimeException;
import service.ServiceManager;
import service.TrayIconService;

public class DataLoadingException extends ARuntimeException {

	private static final long serialVersionUID = 6001802921964671415L;

	private final TrayIconService ts = ServiceManager.getInstance(TrayIconService.class);

	@Override
	public String getDescription() {
		return dictionnaire.getDataLoadingExceptionDescription().getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getDataLoadingExceptionMessage().getValue();
	}

	@Override
	public Runnable next() {
		return () -> {
			ts.quitter();
		};
	}

}
