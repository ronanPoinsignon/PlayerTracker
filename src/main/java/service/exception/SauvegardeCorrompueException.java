package service.exception;

import modele.exception.AException;

public class SauvegardeCorrompueException extends AException {

	private static final long serialVersionUID = 4377631700576701768L;

	@Override
	public String getDescription() {
		return dictionnaire.getSauvegardeCorrompueExceptionDescription().getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getSauvegardeCorrompueExceptionMessage().getValue();
	}
}