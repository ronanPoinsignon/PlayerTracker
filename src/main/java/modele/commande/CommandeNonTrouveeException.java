package modele.commande;

public class CommandeNonTrouveeException extends Exception {

	private static final String MESSAGE = "La commande n'est pas dans le gestionnaire";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CommandeNonTrouveeException() {
		super(CommandeNonTrouveeException.MESSAGE);
	}

}

