package modele.commande;


public class JoueurDejaPresentException extends Exception {

	private static final String MESSAGE = "Le joueur est déjà présent dans la table";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public JoueurDejaPresentException() {
		super(JoueurDejaPresentException.MESSAGE);
	}

}
