package modele.event.action.exception;

import modele.exception.AException;

public class NoLolInstallationFound extends AException {

	private static final long serialVersionUID = -6845460775683090231L;

	@Override
	public String getDescription() {
		return dictionnaire.getNoLolInstallationFoundDescription().getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getNoLolInstallationFoundMessage().getValue();
	}

}