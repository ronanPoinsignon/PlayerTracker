package appli.exception;

import modele.exception.AException;
import service.ServiceManager;
import service.TrayIconService;

public class ApplicationDejaEnCoursException extends AException {

	private static final long serialVersionUID = 9169089125391404777L;

	private final TrayIconService ts = ServiceManager.getInstance(TrayIconService.class);

	public ApplicationDejaEnCoursException() {
		super("L'application est déjà en cours d'exécution.");
	}

	@Override
	public String getDescription() {
		return "Regardez dans vos icones windows pour y trouver l'application.";
	}

	@Override
	public Runnable next() {
		return () -> {
			ts.quitter();
		};
	}
}