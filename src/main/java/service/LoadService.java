package service;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.StreamCorruptedException;

import appli.exception.DataLoadingException;
import modele.save.DataObject;
import service.exception.SauvegardeCorrompueException;

public class LoadService implements IService {

	private FileManager fm;

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

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
	}
}