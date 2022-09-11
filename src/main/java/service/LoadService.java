package service;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import appli.exception.DataLoadingException;
import modele.joueur.Joueur;
import modele.save.DataObject;
import modele.tache.Tache;
import service.exception.SauvegardeCorrompueException;

public class LoadService implements IService {

	private FileManager fm;
	private AlertFxService alerteService;

	public DataObject load() throws SauvegardeCorrompueException, IOException {
		final var fichier = fm.getOrCreateFile("data.txt");

		try {
			return fm.readSave(fichier);
		} catch(final FileNotFoundException e) {
			fichier.createNewFile();
			return new DataObject();
		} catch (ClassNotFoundException|EOFException|StreamCorruptedException e) {
			throw new SauvegardeCorrompueException();
		} catch (final InvalidClassException e) {
			throw new DataLoadingException();
		}
	}
	
	public Tache<List<Joueur>> asyncLoad() {
		return new Tache<List<Joueur>>() {
			@Override
			protected List<Joueur> call() throws Exception {
				List<Joueur> joueurs;
				
				try {
					final var data = LoadService.this.load();
					joueurs = data.getJoueurs();
				} catch (final SauvegardeCorrompueException e) {
					alerteService.alert(e);
					joueurs = new ArrayList<>();
				}
				
				return joueurs;
			}
		};
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
		alerteService = ServiceManager.getInstance(AlertFxService.class);
	}
}