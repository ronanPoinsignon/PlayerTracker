package modele.webrequestrunnable;

import java.net.ConnectException;
import java.util.Map;

import modele.joueur.Champion;
import modele.joueur.Joueur;
import modele.joueur.Partie;
import modele.request.data.SummonerInGame;
import service.ServiceManager;
import service.WebService;

public abstract class RequestPlayerData implements Runnable {

	protected final WebService webService = ServiceManager.getInstance(WebService.class);

	protected RequestPlayerData() {

	}

	public abstract Map<Joueur, SummonerInGame> request();

	protected void setInfo(final Joueur joueur, final SummonerInGame summoner) {
		if(joueur == null || summoner == null) {
			return;
		}
		joueur.setPseudo(summoner.getSummoner_name());
		if(summoner.isIn_game()) {
			joueur.setPartie(new Partie(summoner.getGame_id(), summoner.getEncryption_key(), new Champion(summoner.getChampion_name(), summoner.getChampion_image()), summoner.getGame_type(),  summoner.getStart_time()));
		}
		// à faire en dernier pour ne déclencher le service de notification uniquement lorsque
		// toutes les informations du joueurs ont été mises à jour
		joueur.setInGame(summoner.isIn_game());
	}

	@Override
	public void run() {
		try {
			try {
				request().forEach(this::setInfo);
			} catch (final RuntimeException e) {
				final var cause = e.getCause();
				if(cause != null) {
					throw cause;
				}
				throw e;
			}
		} catch (final ConnectException e) {
			// pas de connexion à internet, ne rien faire
		} catch(final RuntimeException e) {
			throw e;
		} catch(final Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
