package modele.joueur;

import java.io.Serializable;

public class Serveur implements Serializable {

	private static final long serialVersionUID = 1015191455932008682L;

	private String server_id;
	private final String label;

	public Serveur(final String server_id, final String label) {
		this.server_id = server_id;
		this.label = label;
	}

	public String getServerId() {
		return server_id;
	}

	public void setName(final String server_id) {
		this.server_id = server_id;
	}

	public String getLabel() {
		return label;
	}

}
