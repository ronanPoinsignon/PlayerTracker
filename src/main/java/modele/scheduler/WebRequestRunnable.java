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

	private final WebService webService = ServiceManager.getInstance(WebService.class);

	private final List<Joueur> joueurs = new ArrayList<>();

	@Override
	public void run() {
		joueurs.stream().filter(joueur -> joueur != null && joueur.getPlayerId() != null && !joueur.getPlayerId().isBlank()).forEach(joueur -> {
			final var sumIG = webService.getSummonerGame(joueur.getPlayerId(), joueur.getServer().getServerId()).getData();
			setInfo(joueur, sumIG);
		});
	}

	private void setInfo(final Joueur joueur, final SummonerInGame summoner) {
		if(joueur == null || summoner == null) {
			return;
		}
		joueur.setPseudo(summoner.getSummoner_name());
		if(summoner.isIn_game()) {
			joueur.setPartie(new Partie(summoner.getGame_id(), summoner.getEncryption_key(), new Champion(summoner.getChampion_name(), summoner.getChampion_image())));
		}
		// à faire en dernier pour ne déclencher le service de notification uniquement lorsque
		// toutes les informations du joueurs ont été mises à jour
		joueur.setInGame(summoner.isIn_game());
	}

	public boolean add(final Joueur joueur) {
		return joueurs.add(joueur);
	}

	public boolean remove(final Joueur joueur) {
		return joueurs.remove(joueur);
	}

}