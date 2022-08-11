package modele.event.tache.event;

import javafx.event.Event;
import javafx.event.EventType;
import modele.joueur.Joueur;

public class EventEditJoueurClick extends EventAbstrait {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final EventType<EventEditJoueurClick> EVENT_EDIT_JOUEUR_CLICK = new EventType<>(Event.ANY);
	
	private Joueur joueur;

	public EventEditJoueurClick(Joueur joueur) {
		super(EVENT_EDIT_JOUEUR_CLICK);
		this.joueur = joueur;
	}
	
	public Joueur getJoueur() {
		return joueur;
	}

}
