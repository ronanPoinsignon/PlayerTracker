package controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modele.joueur.JoueurFx;

public class JoueurController extends ElementController<JoueurFx> implements Initializable {

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

	private final BooleanProperty isConnecte = new SimpleBooleanProperty();
	private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		imageJoueur.imageProperty().bind(imageProperty);
		statut.textProperty().bind(Bindings.when(isConnecte).then("En jeu").otherwise("Déconnecté"));

		nom.setMaxWidth(pane.getPrefWidth() - nom.getLayoutX() - 40);
		pseudo.setMaxWidth(pane.getPrefWidth() - pseudo.getLayoutX() - 40);

		isConnecte.addListener((obs, oldV, newV) -> {
			if(element.getValue() == null) {
				return;
			}

			Image tempImage = null;

			if(element.getValue().isInGame()) {
				final var decoder = Base64.getDecoder();
				final var imageBytes = decoder.decode(element.get().getPartie().getChampion().getBase64ChampionImage());
				final var bis = new ByteArrayInputStream(imageBytes);
				tempImage = new Image(bis);
			}

			final var image = tempImage;
			Platform.runLater(() ->	{
				imageProperty.setValue(element.getValue().isInGame() ? image : null);
			});
		});
	}

	@Override
	public ChangeListener<JoueurFx> getOnChangeListenerEvent() {
		return (obs, oldV, newV) -> {
			isConnecte.unbind();
			imageStatut.imageProperty().unbind();
			nom.textProperty().unbind();

			isConnecte.bind(newV.getIsConnecteProperty());
			imageStatut.imageProperty().bind(newV.getImageConnexion());

			nom.textProperty().bind(newV.getNomProperty());
			pseudo.textProperty().bind(newV.getPseudoProperty());
			System.out.println("oui");
		};
	}

}
