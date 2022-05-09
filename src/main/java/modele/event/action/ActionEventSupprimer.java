package modele.event.action;

import javafx.event.ActionEvent;
import modele.affichage.ViewElement;
import modele.event.eventaction.DeleteEvent;

public class ActionEventSupprimer<T> extends ActionEventHandler {

	private ViewElement<T> table;

	public ActionEventSupprimer(ViewElement<T> table) {
		this.table = table;
	}

	@Override
	public void handle(ActionEvent event) {
		Thread th = new Thread(new DeleteEvent(table));
		th.setDaemon(true);
		th.start();
	}

}
