package modele.commande.exception;

import modele.exception.AException;

public class JoueurDejaPresentException extends AException {

	private static final long serialVersionUID = 8606136356949816532L;

	private static final String MESSAGE = "Le joueur est déjà présent dans la table";

	public JoueurDejaPresentException() {
		super(JoueurDejaPresentException.MESSAGE);
	}

	@Override
	public String getDescription() {
		return "Vous ne pouvez ajouter plusieurs fois le même joueur.";
	}

}