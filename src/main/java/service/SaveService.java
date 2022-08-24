package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modele.joueur.Joueur;

public class SaveService implements IService {

	private final List<Joueur> joueurs = new ArrayList<>();
	private AlertFxService alertService;
	private FileManager fm;

	public void addJoueur(final Joueur joueur) {
		joueurs.add(joueur);
		save();
	}

	public void addJoueurs(final List<Joueur> joueurs) {
		this.joueurs.addAll(joueurs);
		save();
	}

	public void removeJoueur(final Joueur joueur) {
		joueurs.remove(joueur);
		save();
	}

	public void removeJoueurs(final List<Joueur> joueurs) {
		this.joueurs.removeAll(joueurs);
		save();
	}

	public void save() {
		try {
			fm.writeInto(new File("joueurs.txt"), joueurs);
		} catch (final IOException e) {
			alertService.alert(e);
		}
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
		alertService = ServiceManager.getInstance(AlertFxService.class);
	}

}