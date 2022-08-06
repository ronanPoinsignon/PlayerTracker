package service.exception;

import modele.exception.AException;

public class SauvegardeCorrompueException extends AException {

	private static final long serialVersionUID = 4377631700576701768L;

	@Override
	public String getDescription() {
		return dictionnaire.getText("sauvegardeCorrompueExceptionDescription").getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getText("sauvegardeCorrompueExceptionMessage").getValue();
	}
}