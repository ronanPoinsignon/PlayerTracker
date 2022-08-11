package modele.event.tache.event;

import javafx.event.EventType;
import modele.event.tache.handler.EventHandlerTacheUpdateProgress;

/**
 * Evénement transmettant une progression.
 * @author ronan
 *
 */
public class EventTacheUpdateProgress extends EventTacheUpdated {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final EventType<EventTacheUpdateProgress> EVENT_UPDATE_PROGRESS =
			new EventType<>(EventTacheUpdated.EVENT_UPDATE, EventTacheUpdateProgress.class.getName());

	private final long workDone;
	private final long max;

	public EventTacheUpdateProgress(final long workDone, final long max) {
		super(EventTacheUpdateProgress.EVENT_UPDATE_PROGRESS);
		this.workDone = workDone;
		this.max = max;
	}

	public void invokeHandler(final EventHandlerTacheUpdateProgress handler) {
		handler.onUpdateProgress(workDone, max);
	}
}