package modele.commande.exception;

import modele.exception.AException;

public class CommandeNonTrouveeException extends AException {

	private static final long serialVersionUID = -9033354465958272972L;

	@Override
	public String getDescription() {
		return dictionnaire.getCommandeNonTrouveeExceptionDescription().getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getCommandeNonTrouveeExceptionMessage().getValue();
	}

}