package modele.commande.exception;

import modele.exception.AException;

public class PlayerNotFoundException extends AException {

	private static final long serialVersionUID = 5408217942934664110L;

	@Override
	public String getDescription() {
		return dictionnaire.getText("playerNotFoundExceptionDescription").getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getText("playerNotFoundExceptionMessage").getValue();
	}

}