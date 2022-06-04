package modele.commande.exception;

import modele.exception.AException;

public class PlayerNotFoundException extends AException {

	private static final String MESSAGE = "Joueur non trouvé";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PlayerNotFoundException() {
		super(PlayerNotFoundException.MESSAGE);
	}

	@Override
	public String getDescription() {
		return "Le joueur n'est pas dans la liste.";
	}

}