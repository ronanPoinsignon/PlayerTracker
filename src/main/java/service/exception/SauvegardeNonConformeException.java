package service.exception;

public class SauvegardeNonConformeException extends SauvegardeCorrompueException {

	private static final long serialVersionUID = 4859484757236336735L;

	@Override
	public String getDescription() {
		return dictionnaire.getText("sauvegardeNonConformeExceptionDescription").getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getText("sauvegardeNonConformeExceptionMessage").getValue();
	}
}