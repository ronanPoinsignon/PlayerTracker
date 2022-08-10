package modele.webrequestrunnable.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import modele.joueur.Joueur;
import modele.request.data.player.PlayerRequest;
import modele.webrequestrunnable.WebRequestRunnable;

public class WebRequestBuilderForPlayerList extends WebRequestBuilder<WebRequestRunnable> {

	// valeurs par défaut
	private final List<Joueur> joueurs = new ArrayList<>();
	protected Predicate<List<? extends PlayerRequest>> filter = List::isEmpty;

	public WebRequestBuilderForPlayerList setPlayers(final List<? extends Joueur> joueurs) {
		this.joueurs.clear();
		this.joueurs.addAll(joueurs);
		return this;
	}

	public WebRequestBuilderForPlayerList withFilter(final Predicate<List<? extends PlayerRequest>> filter) {
		this.filter = filter;
		return this;
	}

	protected void setFilter(final Predicate<List<? extends PlayerRequest>> filter) {
		this.filter = filter;
	}

	@Override
	public WebRequestRunnable build() {
		final var requestRunnable =  new WebRequestRunnable();
		requestRunnable.setFilter(filter);
		requestRunnable.setPlayers(joueurs);
		return requestRunnable;
	}

}