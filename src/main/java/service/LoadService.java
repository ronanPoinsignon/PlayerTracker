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
import service.exception.SauvegardeCorrompueException;

public class LoadService implements IService {

	private FileManager fm;

	public List<Joueur> load() throws SauvegardeCorrompueException, IOException {
		final var fichier = fm.getOrCreateFile("joueurs.txt");

		try {
			return fm.readList(fichier);
		} catch(final FileNotFoundException e) {
			fichier.createNewFile();
			return new ArrayList<>();
		} catch (ClassNotFoundException|EOFException|StreamCorruptedException e) {
			throw new SauvegardeCorrompueException();
		} catch (final InvalidClassException e) {
			throw new DataLoadingException();
		}
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
	}
}