package modele.affichage;

import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modele.joueur.JoueurFx;

public class PaneViewJoueurFx extends PaneViewElement<JoueurFx> {

	@FXML
	private Pane pane;

	@FXML
	private Label nom;

	@FXML
	private Label pseudo;

	@FXML
	private ImageView imageJoueur;

	@FXML
	private Label statut;

	@FXML
	private ImageView imageStatut;

	public PaneViewJoueurFx() {
		setId("joueursContainer");

		final var paneMap = getPaneMap();
		setSort((node1, node2) -> {
			final var value1 = paneMap.get(node1).getPseudo().toLowerCase();
			final var value2 = paneMap.get(node2).getPseudo().toLowerCase();
			return value1.compareTo(value2);
		});
	}

	@Override
	public FXMLLoader createLoader() throws MalformedURLException {
		final var template = fm.getFileFromResources("fxml/joueur.fxml");
		return new FXMLLoader(template.toURI().toURL());
	}
}
