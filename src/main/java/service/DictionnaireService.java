package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modele.properties.CaselessProperties;

public class DictionnaireService implements IService {

	FileManager fm;
	PropertiesService ps;

	private File langue;

	private Map<String, SimpleStringProperty> textProperties = new HashMap<>();

	public DictionnaireService() {

	}

	/**
	 * Méthode permettant de mettre à jour tous les champs de l'application.
	 * @param langue
	 */
	public void setLangue(final File langue) {
		this.langue = langue;
		final var properties = new CaselessProperties();
		try(var is = new InputStreamReader(new FileInputStream(langue), StandardCharsets.UTF_8)) {
			properties.load(is);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
				
		properties.forEach((k, v) -> {
			final var key = (String) k;
			final var value = (String) v;
			
			var property = textProperties.get(key);
			
			if(property == null) {
				textProperties.put(key, new SimpleStringProperty(value));
			}
			else {
				property.set(value);
			}			
				
		});
	}

	public File getLangue() {
		return langue;
	}
	
	public StringProperty getText(String name) {
		var property = textProperties.get(name.toLowerCase());
		
		if(property == null)
			throw new RuntimeException();
		
		return property;
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
		ps = ServiceManager.getInstance(PropertiesService.class);
		setLangue(fm.getFileFromResources("traductions/" + ps.get("default_language") + ".txt"));
	}
}
