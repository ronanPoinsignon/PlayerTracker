package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation.Status;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
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
import service.DictionnaireService;
import service.ServerManager;
import service.ServiceManager;

public class MainController implements Initializable {

	private static final int TRANSITION_TIME = 300;

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
	
	/* Formulaire d'ajout */

	@FXML
	private GridPane modalAdd;
	
	@FXML
	private Label modalAddTitle;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private TextField nameInput;
	
	@FXML
	private Label pseudoLabel;
	
	@FXML
	private TextField pseudoInput;
	
	@FXML
	private Label serverLabel;
	
	@FXML
	private ChoiceBox<String> serverInput;
	
	@FXML
	private Button addButton;

	private final TranslateTransition openTransition = new TranslateTransition(Duration.millis(MainController.TRANSITION_TIME));
	private final TranslateTransition closeTransition = new TranslateTransition(Duration.millis(MainController.TRANSITION_TIME));

	private final BooleanBinding isTransitionRunningProperty = openTransition.statusProperty().isEqualTo(Status.RUNNING).or(closeTransition.statusProperty().isEqualTo(Status.RUNNING));
	
	private final ServerManager serverManager = ServiceManager.getInstance(ServerManager.class);
	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);

		joueursContainer = new PaneViewElement();
		joueursContainer.setVisible(false);
		
		// Transitions

		openTransition.setNode(modalAdd);

		closeTransition.setNode(modalAdd);
		closeTransition.setOnFinished(evt -> {
			modalAdd.setVisible(false);
		});
		
		// Modal

		modalAdd.setVisible(false);
		modalAdd.widthProperty().addListener((obs, oldV, newV) -> {
			openTransition.setByX(-newV.doubleValue());
			closeTransition.setByX(newV.doubleValue());
		});
		modalAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			event.consume();
		});

		mainContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if(!MouseButton.PRIMARY.equals(event.getButton()) || !modalAdd.isVisible()) {
				return;
			}
			closeModal();
			event.consume();
		});
		
		//Scrollbar

		anchor.getChildren().add(joueursContainer);
		anchor.heightProperty().greaterThan(scrollpane.minHeightProperty()).addListener((obs, oldV, newV) -> {
			if(newV.booleanValue()) {
				scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			} else {
				scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);
			}
		});

		joueursContainer.heightProperty().addListener((obs, oldValue, newValue) -> {
			anchor.setPrefHeight(newValue.doubleValue());
		});
		
		// Formulaire d'ajout
		
		modalAddTitle.setText(dictionnaire.getMenuItemAjouter().get());
		
		nameLabel.setText(dictionnaire.getNomPlaceHolder().get());
		pseudoLabel.setText(dictionnaire.getPseudoPlaceHolder().get());
		serverLabel.setText(dictionnaire.getColonneServeurLegende().get());
		
		addButton.setText(dictionnaire.getMenuItemAjouter().get());
		
		serverInput.setItems(
			FXCollections.observableList(serverManager.getServers().stream().map(Serveur::getLabel).toList())
		);
		
		addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::addJoueur);
		
		// Chargement

		//final String[] nom = {"Théo", "Test", "Théo", "Test", "Théo", "Test", "Théo", "Test", "Théo", "Test", "Théo", "Test"};
		final String[] nom = {"Théo", "Test", "Théo"};
		final var serveur = new Serveur("euw1","euw");
		final String[] pseudo = {"paindemie14", "Guerinoob", "ronan3290", "TheYoloToto", "Makishimu M", "Pléxi", "sanchodecubah", "65 ms player", "deathkay", "Hazyl14", "Tregum", "LALPAGA MOE"};

		final var th = new Thread(() -> {
			final var size = nom.length;
			final var threads = new Thread[size];
			Thread t;

			for(var i = 0; i < size; i++) {
				t = new Thread(new AddEvent(joueursContainer, nom[i], pseudo[i], serveur));
				t.setDaemon(true);
				t.start();
				threads[i] = t;
			}

			try {
				for(final Thread thread : threads) {
					thread.join();
				}

				Platform.runLater(() -> {
					mainContainer.getChildren().remove(loading);
					joueursContainer.setVisible(true);
				});
			}
			catch(final InterruptedException e) {
				ServiceManager.getInstance(AlertFxService.class).alert(e);
			}
		});
		th.setDaemon(true);
		th.start();
	}

	@FXML
	public void openModal() {
		if(modalAdd.isVisible() || isTransitionRunningProperty.getValue()) {
			return;
		}
		modalAdd.setVisible(true);
		openTransition.play();
	}

	public void closeModal() {
		if(!modalAdd.isVisible() || isTransitionRunningProperty.getValue()) {
			return;
		}
		closeTransition.play();
	}
	
	public void addJoueur(MouseEvent event) {
		final var nom = nameInput.getText();
		final var pseudo = pseudoInput.getText();
		final var serverName = serverInput.getValue();
		
		if(nom.isEmpty() || pseudo.isEmpty() || serverName == null)
			return;
				
		final var serverMatches = serverManager.getServers().stream().filter(server -> server.getLabel().equals(serverName)).toList();
		
		if(serverMatches.size() == 0)
			return;
				
		final var serverId = serverMatches.get(0);
		
		Thread t = new Thread(new AddEvent(joueursContainer, nom, pseudo, serverId));
		t.setDaemon(true);
		t.start();
		
		nameInput.setText("");
		pseudoInput.setText("");
		
		closeModal();
		
	}

}