package service;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import appli.exception.DataLoadingException;
import modele.joueur.Joueur;
import modele.tache.Tache;
import service.exception.SauvegardeCorrompueException;

public class LoadService implements IService {

	private AlertFxService alertService;
	private FileManager fm;

	public List<Joueur> load() {
		final var fichier = new File("joueurs.txt");

		try {
			return fm.readList(fichier);
		} catch (ClassNotFoundException|EOFException|StreamCorruptedException e) {
			alertService.alert(new SauvegardeCorrompueException());

			try {
				Files.delete(fichier.toPath());
			} catch (final IOException e1) {
				alertService.alert(e1);
			}

			return new ArrayList<>();
		}
		catch(final FileNotFoundException e) {
			try {
				fichier.createNewFile();
			} catch (final IOException e1) {
				alertService.alert(e1);
			}
			return new ArrayList<>();
		}
		catch (final InvalidClassException e) {
			alertService.alert(new DataLoadingException());
			return new ArrayList<>();
		}
		catch (final IOException e) {
			alertService.alert(e);
			return new ArrayList<>();
		}
	}
	
	public Tache<List<Joueur>> asyncLoad() {
		return new Tache<List<Joueur>>() {
			@Override
			protected List<Joueur> call() throws Exception {
				return LoadService.this.load();
			}
		};
	}

	@Override
	public void init() {
		alertService = ServiceManager.getInstance(AlertFxService.class);
		fm = ServiceManager.getInstance(FileManager.class);
	}
}