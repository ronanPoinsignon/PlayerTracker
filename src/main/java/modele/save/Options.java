package modele.save;

import java.io.File;
import java.io.Serializable;

import modele.localization.Langage;

public class Options implements Serializable {

	private static final long serialVersionUID = -1152942919614928702L;

	String lolPath;
	Langage langage;

	public Options() {

	}

	public String getLolPath() {
		return lolPath;
	}

	public void setLolPath(final File file) {
		if(file == null) {
			lolPath = null;
			return;
		}
		lolPath = file.getAbsolutePath();
	}

	public Langage getLangage() {
		return langage;
	}

	public void setLangage(final Langage langage) {
		this.langage = langage;
	}
}
