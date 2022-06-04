package modele.event.eventaction;

import javafx.scene.control.TableView;

public abstract class RunnableEvent<T> extends EventAction<Void> implements Runnable {

	TableView<T> table;

	protected RunnableEvent(final TableView<T> table) {
		this.table = table;
	}

	@Override
	public void run() {
		execute();
	}
}