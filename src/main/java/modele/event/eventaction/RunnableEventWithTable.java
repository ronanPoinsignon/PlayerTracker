package modele.event.eventaction;

import javafx.scene.control.TableView;

public abstract class RunnableEventWithTable<T> extends RunnableEvent {

	TableView<T> table;

	protected RunnableEventWithTable(final TableView<T> table) {
		this.table = table;
	}
}