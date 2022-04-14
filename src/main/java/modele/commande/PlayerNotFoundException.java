package modele.commande;


public class PlayerNotFoundException extends Exception {

	private static final String MESSAGE = "Le joueur n'est pas dans la liste.";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PlayerNotFoundException() {
		super(PlayerNotFoundException.MESSAGE);
	}

}
