package service;

import java.util.HashMap;

import javafx.stage.DirectoryChooser;

/**
 * Classe permettant une gestion de directoryChooser.
 * @author ronan
 *
 */
public class DirectoryChooserManager implements IService {

	private final HashMap<String, DirectoryChooser> map = new HashMap<>();

	private final DirectoryChooser directoryChooser = new DirectoryChooser();

	public DirectoryChooser getOrCreateInstance(final String instance) {
		if(map.get(instance) == null) {
			map.put(instance, new DirectoryChooserManager().getDirectoryChooser());
		}
		return map.get(instance);
	}

	public DirectoryChooser getInstance(final String instance) {
		return map.get(instance);
	}

	private DirectoryChooser getDirectoryChooser() {
		return directoryChooser;
	}
}