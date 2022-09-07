package modele.localization;

public class Langage {
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
