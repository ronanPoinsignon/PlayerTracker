package modele.save;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import modele.joueur.Joueur;

public class DataObject implements Serializable {

	private static final long serialVersionUID = -2635679306318781212L;

	private final Set<Joueur> joueurs = new HashSet<>();
	private final Options options = new Options();

	public void addJoueur(final Joueur joueur) {
		joueurs.add(joueur);
	}

	public void addJoueurs(final Collection<Joueur> joueurs) {
		this.joueurs.addAll(joueurs);
	}

	public void removeJoueur(final Joueur joueur) {
		joueurs.remove(joueur);
	}

	public void removeJoueurs(final Collection<Joueur> joueurs) {
		this.joueurs.removeAll(joueurs);
	}

	public void setLolPath(final File file) {
		options.setLolPath(file);
	}

	public Set<Joueur> getJoueurs(){
		return joueurs;
	}

	public Options getOptions() {
		return options;
	}
}
