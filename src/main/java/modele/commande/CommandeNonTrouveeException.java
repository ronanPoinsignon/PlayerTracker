package modele.commande;

import modele.exception.AException;

public class CommandeNonTrouveeException extends AException {

	private static final String MESSAGE = "La commande n'est pas dans le gestionnaire";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CommandeNonTrouveeException() {
		super(CommandeNonTrouveeException.MESSAGE);
	}

	@Override
	public String getDescription() {
		return "Impossible d'exécuter la commande voulue.";
	}

}

