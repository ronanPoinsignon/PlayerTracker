package modele.event.action;

import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import modele.event.eventaction.DeleteEvent;
import modele.joueur.JoueurFx;

public class ActionEventSupprimer extends ActionEventHandler {

	private final TableView<JoueurFx> table;

	public ActionEventSupprimer(final TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public void handle(final ActionEvent event) {
		final var th = new Thread(new DeleteEvent(table));
		th.setDaemon(true);
		th.start();
	}

}