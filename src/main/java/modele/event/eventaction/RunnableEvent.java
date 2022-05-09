package modele.event.eventaction;

import modele.affichage.ViewElement;

public abstract class RunnableEvent<T> extends EventAction<Void> implements Runnable {

	ViewElement<T> table;

	protected RunnableEvent(ViewElement<T> table) {
		this.table = table;
	}

	@Override
	public void run() {
		execute();
	}
}
