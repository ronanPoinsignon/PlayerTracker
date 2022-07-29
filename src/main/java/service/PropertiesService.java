package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import modele.properties.CaselessProperties;

public class PropertiesService implements IService {

	private FileManager fm;
	private final Properties properties = new CaselessProperties();

	public String get(final String element) {
		return properties.getProperty(element);
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
		final var f = fm.getFileFromResources("application.properties");
		try(var is = new FileInputStream(f);){
			properties.load(is);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}