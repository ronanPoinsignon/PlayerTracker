package modele.event.action;

import javafx.event.ActionEvent;
import modele.affichage.ViewElement;
import modele.event.eventaction.DeleteEvent;
import modele.joueur.JoueurFx;

public class ActionEventSupprimer extends ActionEventHandler {

	private ViewElement<JoueurFx> table;

	public ActionEventSupprimer(ViewElement<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public void handle(final ActionEvent event) {
		final var th = new Thread(new DeleteEvent(table));
		th.setDaemon(true);
		th.start();
	}

}