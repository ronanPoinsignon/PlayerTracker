package service.exception;

import modele.exception.ARuntimeException;

public class ServiceCreationFailedException extends ARuntimeException {

	private static final long serialVersionUID = 698581549249186029L;

	@Override
	public String getMessageError() {
		return dictionnaire.getText("serviceCreationFailedExceptionMessage").getValue();
	}

	@Override
	public String getDescription() {
		return dictionnaire.getText("serviceCreationFailedExceptionDescription").getValue();
	}

}