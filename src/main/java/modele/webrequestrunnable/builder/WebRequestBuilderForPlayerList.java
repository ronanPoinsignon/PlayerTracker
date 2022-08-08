package modele.webrequestrunnable.builder;

import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;
import modele.webrequestrunnable.FilterRequest;
import modele.webrequestrunnable.WebRequestRunnable;

public class WebRequestBuilderForPlayerList extends WebRequestBuilder<WebRequestRunnable> {

	// valeurs par défaut
	private List<Joueur> joueurs = new ArrayList<>();
	protected FilterRequest filter = List::isEmpty;

	public WebRequestBuilderForPlayerList setPlayers(final List<Joueur> joueurs) {
		this.joueurs = joueurs;
		return this;
	}

	public WebRequestBuilderForPlayerList withFilter(final FilterRequest filter) {
		this.filter = filter;
		return this;
	}

	protected void setFilter(final FilterRequest filter) {
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