package modele.tache;

import javafx.application.Platform;
import javafx.concurrent.Task;
import modele.event.tache.event.EventTacheUpdateMessage;
import modele.event.tache.event.EventTacheUpdated;
import service.AlertFxService;
import service.ServiceManager;

/**
 * Tache abstraite utilisée le maniement d'événements.
 * @author ronan
 *
 * @param <T>
 */
public abstract class Tache<T> extends Task<T> {

	AlertFxService alerteService = ServiceManager.getInstance(AlertFxService.class);

	protected Tache() {
		exceptionProperty().addListener((observable, oldValue, newValue) ->  {
			if(newValue != null) {
				alerteService.alert(newValue);
				this.cancel();
			}
		});
	}

	@Override
	public void updateMessage(final String msg) {
		super.updateMessage(msg);
		fireUpdateEvent(new EventTacheUpdateMessage(msg));
	}

	/*@Override
	public void updateTitle(String title) {
		super.updateMessage(title);
		fireUpdateEvent(new EventTacheUpdateTitle(title));
	}*/

	@Override
	public void updateProgress(final long workDone, final long max) {
		super.updateProgress(workDone, max);
		//fireUpdateEvent(new EventTacheUpdateProgress(workDone, max));
	}

	/*@Override
	public void updateProgress(double workDone, double max) {
		super.updateProgress(workDone, max);
		fireUpdateEvent(new EventTacheUpdateProgress(workDone, max));
	}*/

	@Override
	public void updateValue(final T value) {
		super.updateValue(value);
		//fireUpdateEvent(new EventTacheUpdateValue());
	}

	/**
	 * Active un événement donné.
	 * @param event
	 */
	private void fireUpdateEvent(final EventTacheUpdated event) {
		Platform.runLater(() -> Tache.this.fireEvent(event));
	}
}