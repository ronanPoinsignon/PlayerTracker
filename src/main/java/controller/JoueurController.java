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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modele.joueur.JoueurFx;

public class JoueurController implements Initializable {

	@FXML
	private Pane pane;

	@FXML
	private Label nom;

	@FXML
	private ImageView imageJoueur;

	@FXML
	private Label statut;

	@FXML
	private ImageView imageStatut;

	private final SimpleObjectProperty<JoueurFx> joueur = new SimpleObjectProperty<>();
	private final BooleanProperty isConnecte = new SimpleBooleanProperty();
	private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		joueur.addListener((obs, oldV, newV) -> {
			isConnecte.unbind();
			imageStatut.imageProperty().unbind();
			imageJoueur.imageProperty().unbind();
			nom.textProperty().unbind();
			statut.textProperty().unbind();

			isConnecte.bind(newV.getIsConnecteProperty());
			imageStatut.imageProperty().bind(newV.getImageConnexion());
			imageJoueur.imageProperty().bind(imageProperty);

			nom.textProperty().bind(Bindings.when(newV.getNomProperty().isNull().or(newV.getNomProperty().isEmpty())).then(newV.getPseudo()).otherwise(newV.getNom() + " (" + newV.getPseudo() + ")"));
			statut.textProperty().bind(Bindings.when(isConnecte).then("En jeu").otherwise("Déconnecté"));
		});

		isConnecte.addListener((obs, oldV, newV) -> {
			if(joueur.getValue() == null) {
				return;
			}

			Image tempImage = null;

			if(joueur.getValue().isInGame()) {
				final var decoder = Base64.getDecoder();
				final var imageBytes = decoder.decode(joueur.get().getPartie().getChampion().getBase64ChampionImage());
				final var bis = new ByteArrayInputStream(imageBytes);
				tempImage = new Image(bis);
			}

			final var image = tempImage;
			Platform.runLater(() ->	{
				imageProperty.setValue(joueur.getValue().isInGame() ? image : null);
			});
		});
	}

	public void setJoueur(final JoueurFx joueur) {
		this.joueur.set(joueur);
	}

}
