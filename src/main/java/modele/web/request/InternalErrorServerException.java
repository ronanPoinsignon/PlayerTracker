package modele.web.request;

import modele.exception.ARuntimeException;

public class InternalErrorServerException extends ARuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageError() {
		return dictionnaire.getText("internalErrorServerExceptionDescription").getValue();
	}

	@Override
	public String getDescription() {
		return dictionnaire.getText("internalErrorServerExceptionMessage").getValue();
	}

}
