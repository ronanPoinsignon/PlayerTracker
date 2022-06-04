package modele.commande.exception;

import modele.exception.AException;

public class JoueurDejaPresentException extends AException {

	private static final String MESSAGE = "Le joueur est déjà présent dans la table";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public JoueurDejaPresentException() {
		super(JoueurDejaPresentException.MESSAGE);
	}

	@Override
	public String getDescription() {
		return "Vous ne pouvez ajouter plusieurs fois le même joueur.";
	}

}