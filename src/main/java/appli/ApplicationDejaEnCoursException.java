package appli;

public class ApplicationDejaEnCoursException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 9169089125391404777L;

	public ApplicationDejaEnCoursException() {
		super("L'application est déjà en cours d'exécution.");
	}
}
