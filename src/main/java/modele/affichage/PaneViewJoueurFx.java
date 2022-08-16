package modele.affichage;

import java.net.MalformedURLException;

import javafx.fxml.FXMLLoader;
import modele.affichage.sortstrategy.NameSort;
import modele.joueur.JoueurFx;

public class PaneViewJoueurFx extends PaneViewElement<JoueurFx> {

	public PaneViewJoueurFx() {
		setId("joueursContainer");

		setSort(new NameSort<JoueurFx>());
	}

	@Override
	public FXMLLoader createLoader() throws MalformedURLException {
		final var template = fm.getFileFromResources("fxml/joueur.fxml");
		return new FXMLLoader(template.toURI().toURL());
	}
}
