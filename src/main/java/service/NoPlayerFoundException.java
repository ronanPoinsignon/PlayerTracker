package service;

public class NoPlayerFoundException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 7694093745057444600L;

	public NoPlayerFoundException() {
		super("Le joueur n'a pas été trouvé");
	}

	public NoPlayerFoundException(String msg) {
		super(msg);
	}
}
