package modele.commande.exception;

import modele.exception.AException;

public class CommandeNonTrouveeException extends AException {

	private static final long serialVersionUID = -9033354465958272972L;

	private static final String MESSAGE = "La commande n'est pas dans le gestionnaire";

	public CommandeNonTrouveeException() {
		super(CommandeNonTrouveeException.MESSAGE);
	}

	@Override
	public String getDescription() {
		return "Impossible d'exécuter la commande voulue.";
	}

}