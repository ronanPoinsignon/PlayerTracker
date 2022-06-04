package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import modele.joueur.JoueurFx;

/**
 * Classe permettant de pouvoir sauvegarder et charger des fichiers.
 * @author ronan
 *
 */
public class DirectoryManager implements IService {

	private DirectoryChooserManager directoryChooserManager;

	private static final String TYPE_FICHIER = "*.pt";
	private static final String DESCRIPTION_TYPE_FICHIER = "CONVERTER FILE (.pt)";

	/**
	 * Ouvre une fenêtre de séléction de fichier.
	 * @param window
	 * @param extension
	 * @return
	 */
	public File getFile(final Window window) {
		final var chooser = new FileChooser();
		final var extFilter = new FileChooser.ExtensionFilter(DirectoryManager.DESCRIPTION_TYPE_FICHIER, DirectoryManager.TYPE_FICHIER);
		chooser.getExtensionFilters().add(extFilter);
		final var fichier = chooser.showOpenDialog(window);
		if(fichier != null) {
			directoryChooserManager.getOrCreateInstance("sauvegarder").setInitialDirectory(fichier);
		}
		return fichier;
	}

	public void setDirectory(final File file, final String directoryName) {
		directoryChooserManager.getOrCreateInstance(directoryName).setInitialDirectory(file);
	}

	/**
	 * Ouvre une fenêtre de sélection de dossier.
	 * @param window
	 * @return
	 */
	public File getFolder(final Window window) {
		final var chooser = new DirectoryChooser();
		return chooser.showDialog(window);
	}

	/**
	 * Demande un endroit de sauvegarde et sauvegarde les {@link JoueurFx} donnés dans un fichier.
	 * @param window
	 * @param listeVideos
	 * @throws IOException
	 */
	public void save(final Window window, final List<JoueurFx> listeJoueurs) throws IOException {
		final var chooser = new FileChooser();
		final var directory = directoryChooserManager.getOrCreateInstance("sauvegarder");
		chooser.setInitialDirectory(directory.getInitialDirectory());
		final var extFilter = new FileChooser.ExtensionFilter(DirectoryManager.DESCRIPTION_TYPE_FICHIER, DirectoryManager.TYPE_FICHIER);
		chooser.getExtensionFilters().add(extFilter);
		var file = directory.getInitialDirectory();
		if(file == null) {
			file = chooser.showSaveDialog(window);
		}
		if(file == null) {
			return;
		}
		directory.setInitialDirectory(file);
		try(var os = new FileOutputStream(file);
				var output = new ObjectOutputStream(os)){
			output.writeObject(listeJoueurs);
			output.flush();
		}
	}

	public File setFileDirectory(final Window window, final String directoryName) {
		final var chooser = new FileChooser();
		final var directory = directoryChooserManager.getOrCreateInstance(directoryName);
		chooser.setInitialDirectory(directory.getInitialDirectory());
		var file = directory.getInitialDirectory();
		if(file == null) {
			file = chooser.showOpenDialog(window);
		}
		if(file == null) {
			return null;
		}
		directory.setInitialDirectory(file);
		return directory.getInitialDirectory();
	}

	public File setFolderDirectory(final Window window, final String directoryName) {
		final var chooser = new DirectoryChooser();
		final var directory = directoryChooserManager.getOrCreateInstance(directoryName);
		chooser.setInitialDirectory(directory.getInitialDirectory());
		var file = directory.getInitialDirectory();
		if(file == null) {
			file = chooser.showDialog(window);
		}
		if(file == null) {
			return null;
		}
		directory.setInitialDirectory(file);
		return directory.getInitialDirectory();
	}

	public File getDirectory(final String directoryName) {
		return directoryChooserManager.getOrCreateInstance(directoryName).getInitialDirectory();
	}

	/**
	 * Retourne une liste de {@link JoueurFx} depuis un fichier demandé.
	 * @param window
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public List<JoueurFx> load(final Window window) throws IOException, ClassNotFoundException {
		final var file = getFile(window);
		if(file == null) {
			return new ArrayList<>();
		}
		try(var is = new FileInputStream(file);
				var input = new ObjectInputStream(is)){
			return (List<JoueurFx>) input.readObject();
		}
	}

	@Override
	public void init() {
		directoryChooserManager = ServiceManager.getInstance(DirectoryChooserManager.class);
	}
}