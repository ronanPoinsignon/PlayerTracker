package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;
import modele.joueur.JoueurFx;

public class SaveService implements IService {

	private final List<Joueur> joueurs = new ArrayList<>();
	private final AlertFxService alertService = ServiceManager.getInstance(AlertFxService.class);

	public void addJoueur(final Joueur joueur) {
		joueurs.add(joueur);
		save();
	}

	public void removeJoueur(final Joueur joueur) {
		joueurs.remove(joueur);
		save();
	}

	public void save() {
		File fichier = null;

		fichier = new File("joueurs.txt");
		try {
			fichier.createNewFile();
		} catch (final IOException e) {
			alertService.alert(e);
		}

		try (var oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
			oos.writeInt(joueurs.size());

			for(Joueur joueur : joueurs) {
				if(joueur instanceof JoueurFx) {
					joueur = ((JoueurFx) joueur).getJoueur();
				}

				oos.writeObject(joueur);
			}
		} catch (final IOException e) {
			alertService.alert(e);
		}
	}

}