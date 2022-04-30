package modele.web.request;

public class DataNotFoundException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -5115305139178777898L;

	private static final String MESSAGE = "Aucun résultat pour la requête.";

	public DataNotFoundException() {
		super(DataNotFoundException.MESSAGE);
	}

	public DataNotFoundException(String msg) {
		super(msg);
	}

	public DataNotFoundException(Exception e) {
		super(e);
	}
}
