package modele.web.request;

import modele.exception.ARuntimeException;

public class DataNotFoundException extends ARuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -5115305139178777898L;

	private static final String MESSAGE = "Le joueur n'existe pas";

	public DataNotFoundException() {
		super(DataNotFoundException.MESSAGE);
	}

	public DataNotFoundException(final String msg) {
		super(msg);
	}

	@Override
	public String getDescription() {
		return "Aucun résultat pour le pseudo donné.";
	}
}