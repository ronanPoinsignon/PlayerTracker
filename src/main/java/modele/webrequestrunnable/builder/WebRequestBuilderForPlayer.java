package modele.webrequestrunnable.builder;

import modele.joueur.Joueur;
import modele.webrequestrunnable.WebRequestOnePlayerRunnable;

public class WebRequestBuilderForPlayer extends WebRequestBuilder<WebRequestOnePlayerRunnable> {

	private Joueur joueur;

	public WebRequestBuilderForPlayer(final Joueur joueur) {
		this.joueur = joueur;
	}

	public WebRequestBuilderForPlayer setPlayer(final Joueur joueur) {
		this.joueur = joueur;
		return this;
	}

	@Override
	public WebRequestOnePlayerRunnable build() {
		final var requestRunnable =  new WebRequestOnePlayerRunnable();
		requestRunnable.setJoueur(joueur);
		return requestRunnable;
	}

}