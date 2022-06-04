package modele.event.tache.handler;

import javafx.event.EventHandler;
import modele.event.tache.event.EventTacheUpdateMessage;

/**
 * Handler abstrait servant à la réception de {@link EventTacheUpdateMessage}.
 * @author ronan
 *
 */
public abstract class EventHandlerTacheUpdateMessage implements EventHandler<EventTacheUpdateMessage> {

	@Override
	public void handle(final EventTacheUpdateMessage event) {
		event.invokeHandler(this);
	}

	public abstract void onUpdateMessage(String message);

}