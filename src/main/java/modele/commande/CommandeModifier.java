package modele.commande;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;

public class CommandeModifier extends Commande<JoueurFx> {

	private final Joueur joueur;

	private final String nomModif;
	private final String ancienNom;

	private final String pseudoModif;
	private final String ancienPseudo;

	private final Serveur serveurModif;
	private final Serveur ancienServeur;

	public CommandeModifier(final Joueur joueur, final String nomModif, final String pseudoModif, final Serveur serverModif) {
		this.joueur = joueur;
		this.nomModif = nomModif;
		this.pseudoModif = pseudoModif;
		serveurModif = serverModif;

		ancienNom = joueur.getNom();
		ancienPseudo = joueur.getPseudo();
		ancienServeur = joueur.getServer();
	}

	@Override
	public boolean executer() {
		joueur.setNom(nomModif);
		joueur.setPseudo(pseudoModif);
		joueur.setServer(serveurModif);
		return true;
	}

	@Override
	public boolean annuler() {
		joueur.setNom(ancienNom);
		joueur.setPseudo(ancienPseudo);
		joueur.setServer(ancienServeur);
		return true;
	}

}