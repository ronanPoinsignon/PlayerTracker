package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modele.joueur.JoueurFx;

public class JoueurController implements Initializable {
	
	@FXML
	private Pane pane;
	
	@FXML
	private Label nom;
	
	@FXML
	private ImageView image_joueur;
	
	@FXML
	private Label statut;
	
	@FXML
	private ImageView image_statut;
	
	private SimpleObjectProperty<JoueurFx> joueur = new SimpleObjectProperty<>();
	private BooleanProperty isConnecte = new SimpleBooleanProperty();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.joueur.addListener((obs, oldV, newV) -> {
			nom.setText(newV.getNom()+" ("+newV.getPseudo()+")");
						
			image_statut.imageProperty().bind(newV.getImageConnexion());
		});
				
		this.isConnecte.addListener((obs, oldV, newV) -> {
			if(newV) {
				Platform.runLater(() -> statut.setText("En jeu"));
			}
			else {
				Platform.runLater(() -> statut.setText("Déconnecté"));
			}
		});
	}
	
	public void setJoueur(JoueurFx joueur) {		
		this.joueur.set(joueur);
		
		this.isConnecte.unbind();
		this.isConnecte.bind(joueur.getIsConnecteProperty());
		
		if(joueur.isInGame()) {
			Platform.runLater(() -> statut.setText("En jeu"));
		}
		else {
			Platform.runLater(() -> statut.setText("Déconnecté"));
		}
	}

}
