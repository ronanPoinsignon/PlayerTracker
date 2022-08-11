package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.event.EventType;
import modele.event.tache.event.EventAbstrait;

public class EventService implements IService {
	
	private final Map<EventType<? extends EventAbstrait>, EventsHandler<? extends EventAbstrait>> listeners = new HashMap<>();
	
	public void trigger(EventAbstrait event) {
		final var eventsHandler = listeners.get(event.getEventType());
		eventsHandler.run(event);
	}

	public <T extends EventAbstrait> void addListener(EventType<T> event, EventHandler<? super T> handler) {
		var handlers = (EventsHandler<T>) listeners.get(event);
		
		if(handlers == null) {
			handlers = new EventsHandler<>();
			listeners.put(event, handlers);
		}
		
		handlers.add(handler);
		
	}
	
	public <T extends EventAbstrait> void removeListener(EventType<T> event, EventHandler<? super T> handler) {
		final var handlers = (EventsHandler<T>) listeners.get(event);
		
		if(handlers == null)
			return;
		
		handlers.remove(handler);
	}
	
	public class EventsHandler<T extends EventAbstrait> {
		private final List<EventHandler<? super T>> handlers = new ArrayList<>();
		
		public void add(final EventHandler<? super T> handler) {
			handlers.add(handler);
		}
		
		public void remove(final EventHandler<? super T> handler) {
			handlers.remove(handler);
		}
		
		public void run(final EventAbstrait event) {
			handlers.forEach(handler -> handler.handle((T) event));
		}
	}

}


