package modele.webrequestrunnable;

import java.util.HashMap;
import java.util.Map;

import modele.joueur.Joueur;
import modele.request.data.SummonerInGame;
import modele.request.data.player.PlayerById;

public class WebRequestOnePlayerRunnable extends RequestPlayerData {

	private Joueur joueur;

	public WebRequestOnePlayerRunnable(final Joueur joueur) {
		this.joueur = joueur;
	}

	public WebRequestOnePlayerRunnable() {

	}

	public void setJoueur(final Joueur joueur) {
		this.joueur = joueur;
	}

	@Override
	public Map<Joueur, SummonerInGame> request() {
		if(joueur == null) {
			return new HashMap<>();
		}
		final var sumIG = webService.getSummonerGame(new PlayerById(joueur.getPlayerId(), joueur.getServer().getServerId())).getData();
		final Map<Joueur, SummonerInGame> map = new HashMap<>();
		map.put(joueur, sumIG);
		return map;
	}
}
