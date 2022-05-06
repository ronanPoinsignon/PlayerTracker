package service.exception;

public class SauvegardeCorrompueException extends Exception {

	private static final long serialVersionUID = 4377631700576701768L;

	public SauvegardeCorrompueException() {
		super("La sauvegarde est vide ou corrompue, elle ne peut donc pas être chargée");
	}
}
