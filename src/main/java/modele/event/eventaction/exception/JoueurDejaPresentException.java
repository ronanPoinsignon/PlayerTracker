package modele.event.eventaction.exception;

public class JoueurDejaPresentException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 2906957799299998669L;

	private static final String MESSAGE = "Le joueur est déjà présent";

	public JoueurDejaPresentException() {
		super(JoueurDejaPresentException.MESSAGE);
	}

	public JoueurDejaPresentException(String pseudo) {
		super(pseudo + " est déjà présent");
	}
}
