package service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.event.EventType;
import modele.event.tache.event.EventAbstrait;
import modele.event.tache.handler.AbstractEventHandler;

public class EventService implements IService {
	
	private final Map<EventType<? extends EventAbstrait>, Set<EventHandler<? super EventAbstrait>>> listeners = new HashMap<>();
	
	public <T extends EventAbstrait> void trigger(T event) {
		listeners.get(event.getEventType()).forEach(handler -> handler.handle(event));
	}

	public <T extends EventAbstrait> void addListener(EventType<T> event, EventHandler<? super T> handler) {
		var set = listeners.get(event);
		
		if(set == null) {
			set = new HashSet<>();
			listeners.put(event, set);
		}
		
		set.add(handler);
		
	}
	
	public void removeListener(EventType<? extends EventAbstrait> event, AbstractEventHandler<EventAbstrait> handler) {
		final var set = listeners.get(event);
		
		if(set == null)
			return;
		
		set.remove(handler);
	}

}
