package modele.save;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;

public class DataObject implements Serializable {

	private static final long serialVersionUID = -2635679306318781212L;

	private final List<Joueur> joueurs = new ArrayList<>();
	private final Options options = new Options();

	public void addJoueur(final Joueur joueur) {
		joueurs.add(joueur);
	}

	public void addJoueurs(final List<Joueur> joueurs) {
		this.joueurs.addAll(joueurs);
	}

	public void removeJoueur(final Joueur joueur) {
		joueurs.remove(joueur);
	}

	public void removeJoueurs(final List<Joueur> joueurs) {
		this.joueurs.removeAll(joueurs);
	}

	public void setLolPath(final File file) {
		options.setLolPath(file);
	}

	public List<Joueur> getJoueurs(){
		return joueurs;
	}

	public Options getOptions() {
		return options;
	}
}
