package modele.event.action.exception;

import modele.exception.AException;

public class RegarderProcessException extends AException {

	private static final long serialVersionUID = -1793510066978472310L;

	private static final String MESSAGE = "La partie n'a pu être lancée.";

	public RegarderProcessException() {
		super(RegarderProcessException.MESSAGE);
	}

	@Override
	public String getDescription() {
		return "Vérifiez que le dossier d'installation de league of Legends est correct et recommencez."
				+ "\nIl est aussi possible que l'erreur vienne de League of Legends.";
	}

}