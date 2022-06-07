package controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
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

	private SimpleObjectProperty<JoueurFx> joueur = new SimpleObjectProperty<>();
	private BooleanProperty isConnecte = new SimpleBooleanProperty();

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		joueur.addListener((obs, oldV, newV) -> {
			nom.setText(newV.getNom() + " (" + newV.getPseudo() + ")");

			imageStatut.imageProperty().bind(newV.getImageConnexion());
		});

		isConnecte.addListener((obs, oldV, newV) -> {
			Image tempImage = null;
			
			if(newV.booleanValue()) {
				Decoder decoder = Base64.getDecoder();
				byte[] imageBytes = decoder.decode(joueur.get().getPartie().getChampion().getBase64ChampionImage());
				ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
				tempImage = new Image(bis);
			}
			
			final Image image = tempImage;
			
			Platform.runLater(() ->	{
				statut.setText(Boolean.TRUE.equals(newV) ? "En jeu" : "Déconnecté");
				imageJoueur.setImage(newV.booleanValue() ? image : null);
			});
		});
	}

	public void setJoueur(JoueurFx joueur) {
		this.joueur.set(joueur);

		isConnecte.unbind();
		isConnecte.bind(joueur.getIsConnecteProperty());

		Platform.runLater(() -> statut.setText(joueur.isInGame() ? "En jeu" : "Déconnecté"));
	}

}
