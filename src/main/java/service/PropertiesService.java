package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService implements IService {

	private FileManager fm = ServiceManager.getInstance(FileManager.class);
	private Properties properties = new Properties();

	public PropertiesService() throws IOException {
		File f = fm.getFileFromResources("application.properties");
		InputStream is = new FileInputStream(f);
		properties.load(is);
	}

	public String get(String element) {
		return properties.getProperty(element);
	}
}
