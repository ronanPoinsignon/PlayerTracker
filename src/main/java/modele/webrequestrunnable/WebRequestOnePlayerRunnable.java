package modele.webrequestrunnable;

import modele.joueur.Joueur;

public class WebRequestOnePlayerRunnable extends RequestPlayerData {

	private final Joueur joueur;

	public WebRequestOnePlayerRunnable(final Joueur joueur) {
		this.joueur = joueur;
	}

	@Override
	public void run() {
		request(joueur);
	}
}
