package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modele.localization.Langage;
import modele.properties.CaselessProperties;

public class DictionnaireService implements IService {

	FileManager fm;
	PropertiesService ps;
	LangagesManager langagesManager;
	SaveService saveService;

	private Langage langue;

	private final Map<String, SimpleStringProperty> textProperties = new HashMap<>();

	public DictionnaireService() {

	}

	/**
	 * Méthode permettant de mettre à jour tous les champs de l'application.
	 * @param langue
	 */
	public void setLangue(final Langage langue) {
		this.langue = langue;
		
		final var file = fm.getFileFromResources("traductions/" + langue.getFileName() + ".txt");
		
		final var properties = new CaselessProperties();
		try(var is = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
			properties.load(is);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		saveService.setLangage(langue);

		properties.forEach((k, v) -> {
			final var key = (String) k;
			final var value = (String) v;

			final var property = textProperties.get(key);

			if(property == null) {
				textProperties.put(key, new SimpleStringProperty(value));
			}
			else {
				property.set(value);
			}
		});
	}

	public Langage getLangue() {
		return langue;
	}

	public StringProperty getText(final String name) {
		final var property = textProperties.get(name.toLowerCase());

		if(property == null) {
			throw new RuntimeException();
		}

		return property;
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
		ps = ServiceManager.getInstance(PropertiesService.class);
		langagesManager = ServiceManager.getInstance(LangagesManager.class);
		saveService = ServiceManager.getInstance(SaveService.class);
	}
}
