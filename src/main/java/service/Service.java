package service;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe a pour but de faire une gestion de services pouvant être utilisés n'importe où dans l'application
 * en permettant l'unicité de chaque service.
 * @author ronan
 *
 */
public abstract class Service {

	private static Map<Class<? extends Service>, Service> services = new HashMap<>();

	protected Service() {

	}

	public static final <T extends Service> T getInstance(Class<T> clazz) {
		T service = (T) Service.services.get(clazz);
		if(service == null) {
			try {
				service = clazz.newInstance();
				Service.services.put(clazz, service);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new ServiceCreationFailedException();
			}
		}
		return service;
	}
}
