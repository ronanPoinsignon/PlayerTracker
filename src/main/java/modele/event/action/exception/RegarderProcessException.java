package modele.event.action.exception;

import modele.exception.AException;

public class RegarderProcessException extends AException {

	private static final long serialVersionUID = -1793510066978472310L;

	@Override
	public String getDescription() {
		return dictionnaire.getText("regarderProcessExceptionDescription").getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getText("regarderProcessExceptionMessage").getValue();
	}

}