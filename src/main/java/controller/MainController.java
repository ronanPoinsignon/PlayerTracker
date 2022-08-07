package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation.Status;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
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
import modele.commande.CommandeAjout;
import modele.event.eventaction.AddEvent;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;
import service.DictionnaireService;
import service.GestionnaireCommandeService;
import service.LoadService;
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

	private BooleanBinding openModalBinding;
	private BooleanBinding closeModalBinding;

	private final ServerManager serverManager = ServiceManager.getInstance(ServerManager.class);
	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	private final PropertiesService ps = ServiceManager.getInstance(PropertiesService.class);
	private final LoadService loadService = ServiceManager.getInstance(LoadService.class);
	private final GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		openModalBinding = isTransitionRunningProperty.or(modalAdd.visibleProperty());
		closeModalBinding = isTransitionRunningProperty.or(modalAdd.visibleProperty().not());

		joueursContainer = new PaneViewElement();
		joueursContainer.setVisible(false);

		addButton.disableProperty().bind(openModalBinding.not());

		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
			if (!MouseButton.PRIMARY.equals(evt.getButton()) || closeModalBinding.getValue()) {
				evt.consume();
			}
		});
		scrollpane.setOnMousePressed(event -> {
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

		addButton.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
			if (!MouseButton.PRIMARY.equals(evt.getButton())) {
				evt.consume();
			}
		});
		addButton.setOnMousePressed(this::addJoueur);

		// Chargement
		
		final var loadTask = loadService.asyncLoad();
		loadTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, workerStateEvent -> {
			final var joueurs = loadTask.getValue();
						
			joueurs.stream()
			.map(JoueurFx::new)
			.map(joueur -> new CommandeAjout(joueursContainer, joueur))
			.forEach(commande -> gestionnaireCommandeService.addCommande(commande).executer());
			gestionnaireCommandeService.viderCommandes();
			
			mainContainer.getChildren().remove(loading);
			joueursContainer.setVisible(true);
		});

		final var thread = new Thread(loadTask);
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	public void openModal() {
		modalAdd.setVisible(true);
		openTransition.play();
	}

	public void closeModal() {
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