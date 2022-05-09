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
		System.out.println(anchor);
	}
	
	@FXML
	public void openModal() {
		String[] nom = new String[] {"bite1", "bite2", "bite3"};
		String[] pseudo = new String[] {"Guerinoob", "Tregum", "ronan3290"};
		
		Thread t;
		
		for(int i = 0; i < 3; i++) {
			t = new Thread(new AddEvent(joueursContainer, nom[i], pseudo[i]));
			t.setDaemon(true);
			t.start();
		}

	}

}
