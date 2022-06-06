package appli.exception;

import modele.exception.ARuntimeException;
import service.ServiceManager;
import service.TrayIconService;

public class DataLoadingException extends ARuntimeException {

	private static final long serialVersionUID = 6001802921964671415L;

	private static final String MESSAGE = "Impossible de charger les données";

	private final TrayIconService ts = ServiceManager.getInstance(TrayIconService.class);

	public DataLoadingException() {
		super(DataLoadingException.MESSAGE);
	}

	@Override
	public String getDescription() {
		return "Veuillez supprimer le fichier de sauvegarde \"joueurs.txt\"";
	}

	@Override
	public Runnable next() {
		return () -> {
			ts.quitter();
		};
	}

}
