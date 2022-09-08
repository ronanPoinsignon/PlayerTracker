package modele.save;

import java.io.File;
import java.io.Serializable;

public class Options implements Serializable {

	private static final long serialVersionUID = -1152942919614928702L;

	String lolPath;
	String languePath;

	public Options() {

	}

	public String getLolPath() {
		return lolPath;
	}

	public void setLolPath(final File file) {
		if(file == null) {
			lolPath = null;
		}
		lolPath = file.getAbsolutePath();
	}

	public String getLanguePath() {
		return languePath;
	}

	public void setLanguePath(final File file) {
		if(file == null) {
			languePath = null;
		}
		languePath = file.getAbsolutePath();
	}
}
