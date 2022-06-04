package appli;

import modele.exception.AException;

public class ApplicationDejaEnCoursException extends AException {

	private static final long serialVersionUID = 9169089125391404777L;

	public ApplicationDejaEnCoursException() {
		super("L'application est déjà en cours d'exécution.");
	}

	@Override
	public String getDescription() {
		return "Regardez dans vos icones windows pour y trouver l'application.";
	}
}