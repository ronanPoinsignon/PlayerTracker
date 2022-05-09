package modele.tache;

import java.io.UncheckedIOException;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import modele.request.data.SummonerData;
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

	private String nom;
	private String pseudo;

	public TacheCharger(String nom, String pseudo) {
		this.nom = nom;
		this.pseudo = pseudo;
	}

	@Override
	protected JoueurFx call() throws DataNotFoundException {
		var joueur = new Joueur(nom, pseudo);
		updateMessage("chargement de " + joueur.getAppellation());
		try {
			SummonerData summoner = webService.getSummonerByName(joueur.getPseudo()).getData();
			setInfo(joueur, summoner);
		} catch (DataNotFoundException | UncheckedIOException e) {
			updateMessage("");
			throw e;
		}
		return new JoueurFx(joueur);
	}

	private void setInfo(Joueur joueur, SummonerData summoner) {
		if(joueur == null || summoner == null) {
			return;
		}
		joueur.setPseudo(summoner.getName());
		joueur.setPlayerId(summoner.getSummoner_id());
	}
}
