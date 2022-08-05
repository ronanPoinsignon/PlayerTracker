package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import modele.affichage.PaneViewElement;
import modele.event.eventaction.AddEvent;
import modele.joueur.Serveur;
import service.AlertFxService;
import service.ServiceManager;

public class MainController implements Initializable {
	@FXML
	private StackPane mainContainer;
	
	@FXML
	private AnchorPane anchor;

	@FXML
	private ScrollPane scrollpane;

	private PaneViewElement joueursContainer;

	@FXML
	private Circle open_modal;
	
	@FXML
	private Text text_circle;
	
	@FXML
	private ProgressIndicator loading;
	
	@FXML
	private GridPane modalAdd;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		joueursContainer = new PaneViewElement();
		joueursContainer.setVisible(false);
		
		modalAdd.setVisible(false);
		
		anchor.getChildren().add(joueursContainer);

		joueursContainer.heightProperty().addListener((obs, oldValue, newValue) -> {
			anchor.setPrefHeight(newValue.doubleValue());
		});

		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);

		if(anchor.getHeight() > scrollpane.getMinHeight()) {
			scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		} else {
			scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);
		}

		anchor.heightProperty().addListener((obs,oldValue, newValue) -> {
			if(newValue.doubleValue() > scrollpane.getMinHeight()) {
				scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			} else {
				scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);
			}
		});
				
		final String[] nom = {"Théo", "Test", "Théo", "Test", "Théo", "Test", "Théo", "Test", "Théo", "Test", "Théo", "Test"};
		//final String[] nom = {"Théo", "Test", "Théo"};
		final var serveur = new Serveur("euw1","euw");
		final String[] pseudo = {"paindemie14", "Guerinoob", "ronan3290", "TheYoloToto", "Makishimu M", "Pléxi", "sanchodecubah", "65 ms player", "deathkay", "Hazyl14", "Tregum", "LALPAGA MOE"};

		Thread th = new Thread(() -> {
			List<Thread> threads = new ArrayList<>();
			Thread t;
			
			try {
				for(var i = 0; i < nom.length; i++) {
					t = new Thread(new AddEvent(joueursContainer, nom[i], pseudo[i], serveur));
					t.setDaemon(true);
					t.start();
					threads.add(t);
				}
				
				for(Thread thread : threads) {
					thread.join();
				}
				
				Platform.runLater(() -> {
					System.out.println("Ajout terminé");
					mainContainer.getChildren().remove(loading);
					joueursContainer.setVisible(true);
				});
			}
			catch(InterruptedException e) {
				ServiceManager.getInstance(AlertFxService.class).alert(e);
			}
		});
		th.setDaemon(true);
		th.start();
		
		mainContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if(!event.getButton().equals(MouseButton.PRIMARY) || !modalAdd.isVisible())
				return;
						
			closeModal();
			event.consume();
		});
		
		modalAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			event.consume();
		});
		
	}

	@FXML
	public void openModal(MouseEvent event) {
		event.consume();
		
		if(modalAdd.isVisible())
			return;
		
		modalAdd.setVisible(true);
		
		TranslateTransition transition = new TranslateTransition();
		transition.setNode(modalAdd);
		transition.setDuration(Duration.millis(500));
		transition.setByX(-modalAdd.getWidth());
		
		transition.play();
		
	}
	
	public void closeModal() {
		if(!modalAdd.isVisible())
			return;
		
		TranslateTransition transition = new TranslateTransition();
		transition.setNode(modalAdd);
		transition.setDuration(Duration.millis(500));
		transition.setByX(modalAdd.getWidth());
		
		transition.setOnFinished(event -> {
			modalAdd.setVisible(false);
		});
		
		transition.play();
	}

}
