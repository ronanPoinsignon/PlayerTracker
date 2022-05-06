package modele.event.eventaction.exception;

import modele.exception.ARuntimeException;
import modele.exception.IException;

public class JoueurDejaPresentException extends ARuntimeException implements IException {

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

	@Override
	public String getDescription() {
		return "Vous ne pouvez pas ajouter un joueur plusieurs fois.";
	}
}
