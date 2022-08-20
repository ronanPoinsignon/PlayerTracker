package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Animation.Status;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.StringConverter;
import modele.affichage.PaneViewJoueurFx;
import modele.affichage.propertyutil.impl.StringMajusculeBinding;
import modele.affichage.sortstrategy.NameSort;
import modele.affichage.sortstrategy.PseudoSort;
import modele.affichage.sortstrategy.ServerSort;
import modele.affichage.sortstrategy.SortStrategy;
import modele.commande.CommandeAjout;
import modele.commande.CommandeModifier;
import modele.event.clavier.ClavierEventHandler;
import modele.event.eventaction.AddEvent;
import modele.event.tache.event.EventEditJoueurClick;
import modele.event.tache.event.EventJoueurEdited;
import modele.event.tache.event.EventSortSelect;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;
import service.DictionnaireService;
import service.EventService;
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
	private GridPane gridpane;

	@FXML
	private ScrollPane scrollpane;
	
	@FXML
	private Label labelSort;
	
	@FXML
	private ChoiceBox<SortStrategy<JoueurFx>> sortSelect;

	private PaneViewJoueurFx joueursContainer;

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

	@FXML
	private Button editButton;

	private final TranslateTransition openTransition = new TranslateTransition(Duration.millis(MainController.TRANSITION_TIME));
	private final TranslateTransition closeTransition = new TranslateTransition(Duration.millis(MainController.TRANSITION_TIME));

	private final BooleanBinding isTransitionRunningProperty = openTransition.statusProperty().isEqualTo(Status.RUNNING).or(closeTransition.statusProperty().isEqualTo(Status.RUNNING));

	private BooleanBinding openModalBinding;
	private BooleanBinding closeModalBinding;

	private final BooleanProperty isEditing = new SimpleBooleanProperty();

	private final ServerManager serverManager = ServiceManager.getInstance(ServerManager.class);
	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	private final PropertiesService ps = ServiceManager.getInstance(PropertiesService.class);
	private final LoadService loadService = ServiceManager.getInstance(LoadService.class);
	private final GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);
	private final EventService eventService = ServiceManager.getInstance(EventService.class);

	private final static double SCROLL_SPEED = 10;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		openModalBinding = isTransitionRunningProperty.or(modalAdd.visibleProperty());
		closeModalBinding = isTransitionRunningProperty.or(modalAdd.visibleProperty().not());

		joueursContainer = new PaneViewJoueurFx();
		joueursContainer.setVisible(false);

		addButton.disableProperty().bind(openModalBinding.not());

		addButton.visibleProperty().bind(isEditing.not());
		editButton.visibleProperty().bind(isEditing);
		pseudoInput.disableProperty().bind(isEditing);
		serverInput.disableProperty().bind(isEditing);
		isEditing.set(false);
		
		//SortSelect
		sortSelect.setItems(
			FXCollections.observableList(
				List.of(
					new NameSort<JoueurFx>(), 
					new PseudoSort<JoueurFx>(),
					new ServerSort<JoueurFx>()
				)
			)
		);
		sortSelect.setValue(sortSelect.getItems().get(0));
		sortSelect.setConverter(new StringConverter<SortStrategy<JoueurFx>>() {

			@Override
			public String toString(SortStrategy<JoueurFx> object) {
				return object.getLabelProperty().get();
			}

			@Override
			public SortStrategy<JoueurFx> fromString(String string) {
				return sortSelect.getItems().stream().filter(strategy -> strategy.getLabelProperty().get().equals(string)).findFirst().orElse(null);
			}
			
		});
		sortSelect.setOnAction(evt -> {
			eventService.trigger(new EventSortSelect(sortSelect.getValue()));
		});
		
		labelSort.textProperty().bind(dictionnaire.getText("labelSort"));

		//Scrollbar

		gridpane.add(joueursContainer, 0, 1);

		gridpane.prefHeightProperty().bind(joueursContainer.prefHeightProperty());

		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
			if (!MouseButton.PRIMARY.equals(evt.getButton())) {
				evt.consume();
			}
		});
		scrollpane.setOnMousePressed(event -> {
			closeModal();
		});
		scrollpane.vbarPolicyProperty().bind(
				Bindings.when(gridpane.heightProperty().greaterThan(scrollpane.minHeightProperty()))
				.then(ScrollBarPolicy.ALWAYS)
				.otherwise(ScrollBarPolicy.NEVER));

		scrollpane.getContent().setOnScroll(event -> {
			final var delta = event.getDeltaY() * MainController.SCROLL_SPEED;
			final var height = scrollpane.getContent().getBoundsInLocal().getHeight();
			scrollpane.setVvalue(scrollpane.getVvalue() - delta / height);
		});

		// Transitions

		openTransition.setNode(modalAdd);

		closeTransition.setNode(modalAdd);
		closeTransition.setOnFinished(evt -> {
			modalAdd.setVisible(false);
			isEditing.set(false);
		});

		// Modal

		modalAdd.setVisible(false);
		openTransition.byXProperty().bind(modalAdd.widthProperty().multiply(-1));
		closeTransition.byXProperty().bind(modalAdd.widthProperty());

		modalAdd.setOnMousePressed(MouseEvent::consume);

		// Formulaire d'ajout
		modalAddTitle.textProperty().bind(
				Bindings.when(isEditing)
				.then(new StringMajusculeBinding(dictionnaire.getText("menuItemModifier")))
				.otherwise(new StringMajusculeBinding(dictionnaire.getText("menuItemAjouter")))
				);
		nameLabel.textProperty().bind(new StringMajusculeBinding(dictionnaire.getText("nomPlaceHolder")));
		pseudoLabel.textProperty().bind(new StringMajusculeBinding(dictionnaire.getText("pseudoPlaceHolder")));
		serverLabel.textProperty().bind(new StringMajusculeBinding(dictionnaire.getText("colonneServeurLegende")));

		addButton.textProperty().bind(dictionnaire.getText("menuItemAjouter"));
		editButton.textProperty().bind(dictionnaire.getText("menuItemModifier"));

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

		addButton.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> MouseButton.PRIMARY.equals(evt.getButton()));
		addButton.setOnAction(this::addJoueur);

		// Chargement

		final var loadTask = loadService.asyncLoad();
		loadTask.setOnSucceeded(workerStateEvent -> {
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

		// Modification

		editButton.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> MouseButton.PRIMARY.equals(evt.getButton()));
		editButton.setOnAction(this::editJoueur);

		eventService.addListener(EventEditJoueurClick.EVENT_EDIT_JOUEUR_CLICK, event -> {
			final var joueur = event.getJoueur();
			pseudoInput.setText(joueur.getPseudo());
			nameInput.setText(joueur.getNom());
			serverInput.setValue(joueur.getServer());
			isEditing.set(true);

			if(closeModalBinding.get() && !isTransitionRunningProperty.get()) {
				openModal();
			}
		});

		mainContainer.addEventHandler(KeyEvent.KEY_PRESSED, new ClavierEventHandler(joueursContainer));

	}

	@FXML
	public void openModal() {
		if(openModalBinding.get())
			return;
		
		modalAdd.setVisible(true);
		openTransition.play();
	}

	public void closeModal() {
		if(closeModalBinding.get())
			return;
		
		closeTransition.play();

		nameInput.setText("");
		pseudoInput.setText("");
	}

	public void addJoueur(final ActionEvent event) {
		var nom = nameInput.getText();
		final var pseudo = pseudoInput.getText();
		final var server = serverInput.getValue();

		if(pseudo.isEmpty() || server == null) {
			return;
		}

		if(nom.isEmpty()) {
			nom = pseudo;
		}

		final var t = new Thread(new AddEvent(joueursContainer, nom, pseudo, server));
		t.setDaemon(true);
		t.start();

		closeModal();

	}

	public void editJoueur(final ActionEvent event) {
		final var joueur = joueursContainer.getItems().get(joueursContainer.getSelectedIndex());

		var nom = nameInput.getText();
		final var pseudo = joueur.getPseudo();

		if(nom.isEmpty()) {
			nom = pseudo;
		}

		gestionnaireCommandeService.addCommande(new CommandeModifier(joueur, nom, pseudo, joueur.getServer())).executer();
		eventService.trigger(new EventJoueurEdited(joueur));

		closeModal();
	}

}

