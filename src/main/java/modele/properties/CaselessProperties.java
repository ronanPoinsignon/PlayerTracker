package modele.properties;

import java.util.Properties;

/**
 * Classe gérant les propriétés d'un fichier sans se soucier de la casse.
 * @author ronan
 *
 */
public class CaselessProperties extends Properties {

	private static final long serialVersionUID = -1121659260302910385L;

	@Override
	public synchronized Object put(final Object key, final Object value) {
		final var lowercase = ((String) key).toLowerCase();
		return super.put(lowercase, value);
	}

	@Override
	public String getProperty(final String key) {
		final var lowercase = key.toLowerCase();
		return super.getProperty(lowercase);
	}

	@Override
	public String getProperty(final String key, final String defaultValue) {
		final var lowercase = key.toLowerCase();
		return super.getProperty(lowercase, defaultValue);
	}

}
