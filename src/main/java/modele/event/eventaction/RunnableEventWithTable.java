package modele.event.eventaction;

import modele.affichage.ViewElement;

public abstract class RunnableEventWithTable<T> extends RunnableEvent {

	ViewElement<T> table;

	public RunnableEventWithTable(final ViewElement<T> table) {
		this.table = table;
	}
}