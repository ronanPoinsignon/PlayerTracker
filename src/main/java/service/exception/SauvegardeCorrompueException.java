package service.exception;

import modele.exception.AException;

public class SauvegardeCorrompueException extends AException {

	private static final long serialVersionUID = 4377631700576701768L;

	public SauvegardeCorrompueException() {
		super("Impossible de charger la sauvegarde");
	}

	@Override
	public String getDescription() {
		return "La sauvegarde est vide ou corrompue, elle ne peut donc pas être chargée.";
	}
}