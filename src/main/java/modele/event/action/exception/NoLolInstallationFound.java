package modele.event.action.exception;

import modele.exception.AException;

public class NoLolInstallationFound extends AException {

	/**
	 *
	 */
	private static final long serialVersionUID = -6845460775683090231L;
	private static final String MESSAGE = "L'installation n'a pu être trouvée.";

	public NoLolInstallationFound() {
		super(NoLolInstallationFound.MESSAGE);
	}

	@Override
	public String getDescription() {
		return "Veuillez vérifier que le dossier donné coorespond bien à celui de League of Legends.";
	}

}
