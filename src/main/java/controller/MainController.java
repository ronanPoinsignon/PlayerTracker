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
import javafx.util.StringConverter;
import modele.affichage.PaneViewElement;
import modele.event.eventaction.AddEvent;
import modele.joueur.Serveur;
import service.AlertFxService;
import service.DictionnaireService;
import service.PropertiesService;
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
	private ChoiceBox<Serveur> serverInput;

	@FXML
	private Button addButton;

	private final TranslateTransition openTransition = new TranslateTransition(Duration.millis(MainController.TRANSITION_TIME));
	private final TranslateTransition closeTransition = new TranslateTransition(Duration.millis(MainController.TRANSITION_TIME));

	private final BooleanBinding isTransitionRunningProperty = openTransition.statusProperty().isEqualTo(Status.RUNNING).or(closeTransition.statusProperty().isEqualTo(Status.RUNNING));

	private final ServerManager serverManager = ServiceManager.getInstance(ServerManager.class);
	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	private final PropertiesService ps = ServiceManager.getInstance(PropertiesService.class);

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		joueursContainer = new PaneViewElement();
		joueursContainer.setVisible(false);

		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.setOnMousePressed(event -> {
			if(!MouseButton.PRIMARY.equals(event.getButton()) || !modalAdd.isVisible()) {
				return;
			}
			closeModal();
			event.consume();
		});

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
		modalAdd.setOnMousePressed(event -> {
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

		modalAddTitle.textProperty().bind(dictionnaire.getText("menuItemAjouter"));

		nameLabel.textProperty().bind(dictionnaire.getText("nomPlaceHolder"));
		pseudoLabel.textProperty().bind(dictionnaire.getText("pseudoPlaceHolder"));
		serverLabel.textProperty().bind(dictionnaire.getText("colonneServeurLegende"));

		addButton.textProperty().bind(dictionnaire.getText("menuItemAjouter"));

		serverInput.setItems(FXCollections.observableList(serverManager.getServers()));
		serverInput.setValue(serverManager.getServerById(ps.get("default_server")));
		serverInput.setConverter(new StringConverter<Serveur>() {
			@Override
			public String toString(final Serveur object) {
				return object.getLabel();
			}
			@Override
			public Serveur fromString(final String string) {
				return serverInput.getItems().stream().filter(ap ->
				ap.getServerId().equals(string)).findFirst().orElse(null);
			}
		});

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

	public void addJoueur(final MouseEvent event) {
		final var nom = nameInput.getText();
		final var pseudo = pseudoInput.getText();
		final var server = serverInput.getValue();

		if(nom.isEmpty() || pseudo.isEmpty() || server == null) {
			return;
		}

		final var t = new Thread(new AddEvent(joueursContainer, nom, pseudo, server));
		t.setDaemon(true);
		t.start();

		nameInput.setText("");
		pseudoInput.setText("");

		closeModal();

	}

}