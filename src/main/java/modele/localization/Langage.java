package modele.localization;

import java.io.Serializable;

public class Langage implements Serializable {

	private static final long serialVersionUID = -8055038406940944408L;

	private String name;

	private final String file_name;

	public Langage(final String name, final String file_name) {
		this.name = name;
		this.file_name = file_name;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getFileName() {
		return file_name;
	}

}
