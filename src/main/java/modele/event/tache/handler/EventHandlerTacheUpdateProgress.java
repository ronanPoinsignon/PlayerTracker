package modele.event.tache.handler;

import javafx.event.EventHandler;
import modele.event.tache.event.EventTacheUpdateProgress;

/**
 * Handler abstrait servant à la réception de {@link EventTacheUpdateProgress}.
 * @author ronan
 *
 */
public abstract class EventHandlerTacheUpdateProgress implements EventHandler<EventTacheUpdateProgress> {

	@Override
	public void handle(EventTacheUpdateProgress event) {
		event.invokeHandler(this);
	}

	public abstract void onUpdateProgress(long workDone, long max);

}
