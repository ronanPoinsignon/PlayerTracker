package appli.exception;

import modele.exception.AException;
import service.DictionnaireService;
import service.ServiceManager;
import service.TrayIconService;

public class ApplicationDejaEnCoursException extends AException {

	private static final long serialVersionUID = 9169089125391404777L;

	private final TrayIconService ts = ServiceManager.getInstance(TrayIconService.class);
	protected DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);

	public ApplicationDejaEnCoursException() {
	}

	@Override
	public String getDescription() {
		return dictionnaire.getApplicationDejaEnCoursExceptionDescription().getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getApplicationDejaEnCoursExceptionMessage().getValue();
	}

	@Override
	public Runnable next() {
		return () -> {
			ts.quitter();
		};
	}
}