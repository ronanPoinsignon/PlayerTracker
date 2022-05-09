package controller;

import java.net.URL;
import java.util.ResourceBundle;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.joueur.addListener((obs, oldV, newV) -> {
			nom.setText(newV.getNom()+" ("+newV.getPseudo()+")");
			
			if(newV.getIsConnecteProperty().getValue()) {
				statut.setText("En jeu");
			}
			else {
				statut.setText("Déconnecté");
			}
			
			image_statut.imageProperty().bind(newV.getImageConnexion());
		});
	}
	
	public void setJoueur(JoueurFx joueur) {		
		this.joueur.set(joueur);		
	}

}
