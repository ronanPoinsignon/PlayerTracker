package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import modele.affichage.PaneViewElement;
import modele.event.eventaction.AddEvent;

public class MainController implements Initializable {
	@FXML
	private AnchorPane anchor;
	
	@FXML
	private ScrollPane scrollpane;
	
	private PaneViewElement joueursContainer;
	
	@FXML
	private Circle open_modal;
			
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		joueursContainer = new PaneViewElement();
		
		anchor.getChildren().add(joueursContainer);

		joueursContainer.heightProperty().addListener((obs, oldValue, newValue) -> {
			anchor.setPrefHeight(newValue.doubleValue());
		});
		
		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		if(anchor.getHeight() > scrollpane.getMinHeight())
			scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		else
			scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);

		anchor.heightProperty().addListener((obs,oldValue, newValue) -> {
			if(newValue.doubleValue() > scrollpane.getMinHeight())
				scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			else
				scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);
		});
				
		openModal();
	}
	
	@FXML
	public void openModal() {
		String[] nom = new String[] {"Théo", "Test", "Théo", "Test", "Théo", "Test", "Théo", "Test", "Théo", "Test"};
		//nom = new String[] {"Théo", "Test"};
		String[] pseudo = new String[] {"ruep3", "Guerinoob", "ronan3290", "TheYoloToto", "Makishimu M", "Pléxi", "sanchodecubah", "65 ms player", "deathkay", "SitSonXD"};
		
		Thread t;
		
		for(int i = 0; i < nom.length; i++) {
			t = new Thread(new AddEvent(joueursContainer, nom[i], pseudo[i]));
			t.setDaemon(true);
			t.start();
		}

	}

}
