package modele.webrequestrunnable;

import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;

public class WebRequestRunnable extends RequestPlayerData {

	private final List<Joueur> joueurs = new ArrayList<>();

	@Override
	public void run() {
		joueurs.stream()
		.filter(joueur -> joueur != null && joueur.getPlayerId() != null && !joueur.getPlayerId().isBlank())
		.forEach(this::request);
	}

	public boolean add(final Joueur joueur) {
		return joueurs.add(joueur);
	}

	public boolean remove(final Joueur joueur) {
		return joueurs.remove(joueur);
	}

}