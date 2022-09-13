package modele.event.tache.event;

import javafx.event.Event;
import javafx.event.EventType;
import modele.joueur.Joueur;

public class EventJoueurEdited extends EventAbstrait {

	private static final long serialVersionUID = 1L;
	public static final EventType<EventJoueurEdited> EVENT_JOUEUR_EDITED = new EventType<>(Event.ANY, EventJoueurEdited.class.getName());

	private final Joueur joueur;

	public EventJoueurEdited(final Joueur joueur) {
		super(EventJoueurEdited.EVENT_JOUEUR_EDITED);
		this.joueur = joueur;
	}

	public Joueur getJoueur() {
		return joueur;
	}
}
