package service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe a pour but de faire une gestion de services pouvant être utilisés n'importe où dans l'application
 * en permettant l'unicité de chaque service.
 * @author ronan
 *
 */
public abstract class ServiceManager {

	private static Map<Class<? extends IService>, IService> services = new HashMap<>();

	protected ServiceManager() {

	}

	public static final <T extends IService> T getInstance(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T service = (T) ServiceManager.services.get(clazz);
		if(service == null) {
			try {
				service = clazz.getDeclaredConstructor().newInstance();
				ServiceManager.services.put(clazz, service);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new ServiceCreationFailedException(e);
			}
		}
		return service;
	}
}
