package modele.event.tache.event;

import javafx.event.Event;
import javafx.event.EventType;
import modele.affichage.sortstrategy.SortStrategy;
import modele.joueur.JoueurFx;

public class EventSortSelect extends EventAbstrait {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final EventType<EventSortSelect> EVENT_SORT_SELECT = new EventType<>(Event.ANY, EventSortSelect.class.getName());
	
	private SortStrategy<JoueurFx> sort;

	public EventSortSelect(SortStrategy<JoueurFx> sort) {
		super(EVENT_SORT_SELECT);
		this.sort = sort;
	}
	
	public SortStrategy<JoueurFx> getSort() {
		return sort;
	}

}
