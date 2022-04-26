package modele.tache;

import java.io.IOException;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import service.GestionnaireCommandeService;
import service.NoPlayerFoundException;
import service.ServiceManager;

/**
 * Classe permettant de charger un ou plusieurs joueurs dans la liste de joueurs.
 * @author ronan
 *
 */
public class TacheCharger extends Tache<JoueurFx> {

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
		return new JoueurFx(joueur);
	}
}
