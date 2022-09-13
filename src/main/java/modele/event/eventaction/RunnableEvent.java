package modele.event.eventaction;

import service.AlertFxService;
import service.ServiceManager;

public abstract class RunnableEvent extends EventAction<Void> implements Runnable {

	protected final AlertFxService alerteService = ServiceManager.getInstance(AlertFxService.class);

	@Override
	public void run() {
		try {
			execute();
		} catch (final Exception e) {
			alerteService.alert(e);
		}
	}
}
