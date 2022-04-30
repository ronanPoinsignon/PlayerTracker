package modele.scheduler;

import java.util.ArrayList;
import java.util.List;

import modele.joueur.Champion;
import modele.joueur.Joueur;
import modele.joueur.Partie;
import modele.request.data.SummonerInGame;
import service.ServiceManager;
import service.WebService;

public class WebRequestRunnable implements Runnable {

	private WebService webService = ServiceManager.getInstance(WebService.class);

	private List<Joueur> joueurs = new ArrayList<>();

	@Override
	public void run() {
		joueurs.stream().filter(joueur -> joueur != null && joueur.getPlayerId() != null && !joueur.getPlayerId().isBlank()).forEach(joueur -> {
			SummonerInGame sumIG = webService.getSummonerGame(joueur.getPlayerId()).getData();
			setInfo(joueur, sumIG);
		});
	}

	private void setInfo(Joueur joueur, SummonerInGame summoner) {
		if(joueur == null || summoner == null) {
			return;
		}
		joueur.setPseudo(summoner.getSummoner_name());
		joueur.setInGame(summoner.isIn_game());
		if(summoner.isIn_game()) {
			joueur.setPartie(new Partie(summoner.getGame_id(), summoner.getEncryption_key(), new Champion(summoner.getChampion_name(), summoner.getChampion_image())));
		}
	}

	public void add(Joueur joueur) {
		joueurs.add(joueur);
	}

	public void remove(Joueur joueur) {
		joueurs.remove(joueur);
	}

}
