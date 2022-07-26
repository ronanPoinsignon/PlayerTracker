package modele.exception;

public class JoueurDejaPresentException extends ARuntimeException {

	private static final long serialVersionUID = 8606136356949816532L;

	@Override
	public String getDescription() {
		return dictionnaire.getJoueurDejaPresentExceptionDescription().getValue();
	}

	@Override
	public String getMessageError() {
		return dictionnaire.getJoueurDejaPresentExceptionMessage().getValue();
	}

}