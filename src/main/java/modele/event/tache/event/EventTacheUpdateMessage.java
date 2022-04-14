package modele.event.tache.event;

import javafx.event.EventType;
import modele.event.tache.handler.EventHandlerTacheUpdateMessage;

/**
 * Evénement transmettant un message.
 * @author ronan
 *
 */
public class EventTacheUpdateMessage extends EventTacheUpdated {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final EventType<EventTacheUpdateMessage> EVENT_UPDATE_MESSAGE =
			new EventType<>(EventTacheUpdated.EVENT_UPDATE, "UPDATE_MESSAGE");

	private String message;

	public EventTacheUpdateMessage(String message) {
		super(EventTacheUpdateMessage.EVENT_UPDATE_MESSAGE);
		this.message = message;
	}

	public void invokeHandler(EventHandlerTacheUpdateMessage handler) {
		handler.onUpdateMessage(message);
	}
}
