package modele.event.tache.event;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Evénement abstrait.
 * @author ronan
 *
 */
public abstract class EventAbstrait extends Event {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected EventAbstrait(final EventType<? extends Event> eventType) {
		super(eventType);
	}
}