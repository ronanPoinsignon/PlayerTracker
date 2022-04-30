package modele.scheduler;

import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;
import service.ServiceManager;
import service.WebService;

public class WebRequestRunnable implements Runnable {

	private WebService webService = ServiceManager.getInstance(WebService.class);

	private List<Joueur> joueurs = new ArrayList<>();

	@Override
	public void run() {
		joueurs.stream().map(Joueur::getPlayerId).filter(id -> id != null && !id.isBlank()).forEach(id -> webService.getSummonerGame(id));
	}

	public void add(Joueur joueur) {
		joueurs.add(joueur);
	}

	public void remove(Joueur joueur) {
		joueurs.remove(joueur);
	}

}
