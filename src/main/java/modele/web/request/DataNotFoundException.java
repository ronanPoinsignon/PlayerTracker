package modele.web.request;

import modele.exception.ARuntimeException;

public class DataNotFoundException extends ARuntimeException {

	private static final long serialVersionUID = 3798955127623618929L;

	@Override
	public String getDescription() {
		return dictionnaire.getDataNotFoundExceptionDescription().getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getDataNotFoundExceptionMessage().getValue();
	}
}