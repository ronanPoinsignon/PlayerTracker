package modele.event.action;

import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import modele.event.eventaction.DeleteEvent;
import modele.joueur.JoueurFx;

public class ActionEventSupprimer extends ActionEventHandler {

	private TableView<JoueurFx> table;

	public ActionEventSupprimer(TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public void handle(ActionEvent event) {
		Thread th = new Thread(new DeleteEvent(table));
		th.setDaemon(true);
		th.start();
	}

}
