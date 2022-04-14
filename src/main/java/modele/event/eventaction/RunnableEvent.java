package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;

public abstract class RunnableEvent extends EventAction<Void> implements Runnable {

	TableView<JoueurFx> table;

	protected RunnableEvent(TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public void run() {
		execute();
	}
}
