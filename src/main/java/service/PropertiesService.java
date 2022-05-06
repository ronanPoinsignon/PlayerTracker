package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesService implements IService {

	private FileManager fm;
	private Properties properties = new Properties();

	public String get(String element) {
		return properties.getProperty(element);
	}

	@Override
	public void init() {
		fm = ServiceManager.getInstance(FileManager.class);
		var f = fm.getFileFromResources("application.properties");
		try(var is = new FileInputStream(f);){
			properties.load(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
