package modele.tache;

import java.io.UncheckedIOException;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;
import modele.request.data.SummonerData;
import modele.request.data.player.PlayerByName;
import modele.web.request.DataNotFoundException;
import service.GestionnaireCommandeService;
import service.ServiceManager;
import service.WebService;

/**
 * Classe permettant de charger un ou plusieurs joueurs dans la liste de joueurs.
 * @author ronan
 *
 */
public class TacheCharger extends Tache<JoueurFx> {

	GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);
	WebService webService = ServiceManager.getInstance(WebService.class);

	private final String nom;
	private final String pseudo;
	private final Serveur serveur;

	public TacheCharger(final String nom, final String pseudo, final Serveur serveur) {
		this.nom = nom;
		this.pseudo = pseudo;
		this.serveur = serveur;
	}

	@Override
	protected JoueurFx call() throws DataNotFoundException {
		final var joueur = new Joueur(nom, pseudo, serveur);
		updateMessage("chargement de " + joueur.getAppellation());
		try {
			final var summoner = webService.getSummonerByName(new PlayerByName(joueur.getPseudo(), joueur.getServer().getServerId())).getData();
			setInfo(joueur, summoner);
		} catch (DataNotFoundException | UncheckedIOException e) {
			updateMessage("");
			throw e;
		}
		return new JoueurFx(joueur);
	}

	private void setInfo(final Joueur joueur, final SummonerData summoner) {
		if(joueur == null || summoner == null) {
			return;
		}
		joueur.setPseudo(summoner.getName());
		joueur.setPlayerId(summoner.getSummoner_id());
	}
}