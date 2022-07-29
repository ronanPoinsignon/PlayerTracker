package modele.event.eventaction;

import modele.affichage.ViewElement;
import service.ServiceManager;

public abstract class RunnableEvent extends EventAction<Void> implements Runnable {

	AlertFxService alerteService = ServiceManager.getInstance(AlertFxService.class);
	ViewElement<T> table;

	protected RunnableEvent(ViewElement<T> table) {
		this.table = table;
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (final Exception e) {
			alerteService.alert(e);
		}
	}
}
