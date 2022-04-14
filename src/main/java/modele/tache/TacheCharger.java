package modele.tache;

import java.io.IOException;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import service.GestionnaireCommandeService;
import service.NoPlayerFoundException;
import service.ServiceManager;
import service.WebService;

/**
 * Classe permettant de charger une ou plusieurs vidéos dans la liste de vidéo.
 * @author ronan
 *
 */
public class TacheCharger extends Tache<JoueurFx>{

	WebService webService = ServiceManager.getInstance(WebService.class);
	GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);
	private String nom;
	private String pseudo;

	public TacheCharger(String nom, String pseudo) {
		this.nom = nom;
		this.pseudo = pseudo;
	}

	@Override
	protected JoueurFx call() throws NoPlayerFoundException, IOException {
		Joueur joueur = new Joueur(nom, pseudo);
		updateMessage("chargement de " + joueur.getAppellation());
		setId(joueur);
		return new JoueurFx(joueur);
	}

	private void setId(Joueur joueur) throws NoPlayerFoundException {
		joueur.setId(webService.getIdFromPseudo(joueur.getPseudo()));
	}
}
