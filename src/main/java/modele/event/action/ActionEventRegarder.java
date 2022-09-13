package modele.event.action;

import javafx.event.ActionEvent;
import modele.event.eventaction.RegarderEvent;
import modele.joueur.Joueur;

public class ActionEventRegarder extends ActionEventHandler {

	private final Joueur joueur;

	public ActionEventRegarder(final Joueur joueur) {
		this.joueur = joueur;
	}

	@Override
	public void handle(final ActionEvent event) {
		final var thread = new Thread(new RegarderEvent(joueur));
		thread.setDaemon(true);
		thread.start();
	}

}