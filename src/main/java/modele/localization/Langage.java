package modele.localization;

import java.io.Serializable;

public class Langage implements Serializable {

	private static final long serialVersionUID = -8055038406940944408L;

	private String name;
	
	private String file_name;
	
	public Langage(String name, String file_name) {
		this.name = name;
		this.file_name = file_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return file_name;
	}
	
}
