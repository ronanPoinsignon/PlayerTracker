package service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import modele.joueur.Joueur;
import modele.save.DataObject;

public class SaveService implements IService {

	private final DataObject data = new DataObject();

	private AlertFxService alertService;
	private FileManager fm;

	public void addJoueur(final Joueur joueur) {
		execute(data::addJoueur, joueur);
	}

	public void addJoueurs(final List<Joueur> joueurs) {
		execute(data::addJoueurs, joueurs);
	}

	public void removeJoueur(final Joueur joueur) {
		execute(data::removeJoueur, joueur);
	}

	public void removeJoueurs(final List<Joueur> joueurs) {
		execute(data::removeJoueurs, joueurs);
	}

	public void setLolPath(final File file) {
		execute(data.getOptions()::setLolPath, file);
	}

	public void setLanguePath(final File file) {
		execute(data.getOptions()::setLanguePath, file);
	}

	public <T> void execute(final Consumer<T> data, final T value) {
		data.accept(value);
		save();
	}

	public void save() {
		try {
			fm.writeInto(fm.getOrCreateFile("data.txt"), data);
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