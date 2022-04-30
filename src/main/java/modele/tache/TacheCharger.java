package modele.tache;

import java.io.IOException;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import modele.request.data.SummonerData;
import service.GestionnaireCommandeService;
import service.NoPlayerFoundException;
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
	protected JoueurFx call() throws NoPlayerFoundException, IOException {
		var joueur = new Joueur(nom, pseudo);
		updateMessage("chargement de " + joueur.getAppellation());
		SummonerData summoner = webService.getSummonerByName(joueur.getPseudo()).getData();
		setInfo(joueur, summoner);
		return new JoueurFx(joueur);
	}

	private void setInfo(Joueur joueur, SummonerData summoner) {
		if(joueur == null) {
			return;
		}
		if(summoner == null) {
			return;
		}
		joueur.setPseudo(summoner.getName());
		joueur.setPlayerId(summoner.getSummoner_id());
	}
}
