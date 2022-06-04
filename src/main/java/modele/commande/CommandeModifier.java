package modele.commande;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;

public class CommandeModifier extends Commande<JoueurFx> {

	private final Joueur joueur;
	private final String nomModif;
	private final String ancienNom;

	public CommandeModifier(final Joueur joueur, final String nomModif) {
		this.joueur = joueur;
		this.nomModif = nomModif;
		ancienNom = joueur.getNom();
	}

	@Override
	public boolean executer() {
		joueur.setNom(nomModif);
		return true;
	}

	@Override
	public boolean annuler() {
		joueur.setNom(ancienNom);
		return true;
	}

}