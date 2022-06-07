package modele.affichage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.JoueurController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import modele.joueur.JoueurFx;
import service.FileManager;
import service.ServiceManager;

public class PaneViewElement extends GridPane implements ViewElement<JoueurFx> {
	
	private ObservableList<JoueurFx> elements = FXCollections.observableArrayList(new ArrayList<>());
	private FileManager fm = ServiceManager.getInstance(FileManager.class);
	
	public PaneViewElement() {
		this.setId("joueursContainer");
		
		elements.addListener((ListChangeListener.Change<? extends JoueurFx> change) -> {
			change.next();
			
			if(change.wasAdded()) {
				var template = fm.getFileFromResources("fxml/joueur.fxml");
				
				for(JoueurFx joueur : change.getAddedSubList()) {
					FXMLLoader loader;
					
					try {
						loader = new FXMLLoader(template.toURI().toURL());
						Pane paneJoueur = loader.load();
						
						JoueurController controller = loader.getController();
						
						controller.setJoueur(joueur);
						
						int[] position = calculateNextPosition();
						
						this.getChildren().add(paneJoueur);
												
						paneJoueur.setTranslateX(position[0]);
						paneJoueur.setTranslateY(position[1]);
						
						this.setPrefHeight(position[1] + 240);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					
				}
				
			}
		});
		
	}
	
	public int[] calculateNextPosition() {
		int size = elements.size() - 1;
		
		int rowPosition = size % 3;
		int rowNumber = size / 3;
		
		int x = rowPosition * (300 + 40);
		int y= rowNumber * 200;
		
		return new int[] {x, y};
	}

	@Override
	public ReadOnlyObjectProperty<JoueurFx> selectedItemProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<JoueurFx> getItems() {
		return elements;
	}

}
