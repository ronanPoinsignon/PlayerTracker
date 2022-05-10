package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import modele.affichage.PaneViewElement;
import modele.event.eventaction.AddEvent;

public class MainController implements Initializable {
	@FXML
	private AnchorPane anchor;
	
	private PaneViewElement joueursContainer;
	
	@FXML
	private Circle open_modal;
			
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		joueursContainer = new PaneViewElement();
		
		anchor.getChildren().add(joueursContainer);
				
		openModal();
	}
	
	@FXML
	public void openModal() {
		String[] nom = new String[] {"Théo"};
		String[] pseudo = new String[] {"TheYoloToto"};
		
		Thread t;
		
		for(int i = 0; i < 1; i++) {
			t = new Thread(new AddEvent(joueursContainer, nom[i], pseudo[i]));
			t.setDaemon(true);
			t.start();
		}

	}

}
