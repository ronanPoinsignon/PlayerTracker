package controller;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javafx.animation.Animation.Status;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;
import modele.localization.Langage;
import modele.tache.TacheCharger;
import service.DictionnaireService;
import service.EventService;
import service.GestionnaireCommandeService;
import service.LangagesManager;
import service.PropertiesService;
import service.ServerManager;
import service.ServiceManager;
import service.StageManager;

public class MainController implements Initializable {

	private static final int TRANSITION_TIME = 300;
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private Menu menuLangue;

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
	private Button open_modal;

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
	private final ObjectProperty<Langage> langageProperty = new SimpleObjectProperty<>();

	private final ServerManager serverManager = ServiceManager.getInstance(ServerManager.class);
	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	private final PropertiesService ps = ServiceManager.getInstance(PropertiesService.class);
	private final GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);
	private final EventService eventService = ServiceManager.getInstance(EventService.class);
	private final StageManager stageManager = ServiceManager.getInstance(StageManager.class);
	private final LangagesManager langagesManager = ServiceManager.getInstance(LangagesManager.class);

	private final static double SCROLL_SPEED = 10;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		langageProperty.set(dictionnaire.getLangue());
		
		langageProperty.addListener((obs, oldV, newV) -> {
			dictionnaire.setLangue(newV);
			
			final var value = sortSelect.getValue();
			final var strategies = sortSelect.getItems();
			
			sortSelect.setOnAction(null);
			sortSelect.setItems(FXCollections.observableArrayList());
			sortSelect.setItems(strategies);
			sortSelect.setValue(value);
			sortSelect.setOnAction(this::sortAction);
		});
		
		ToggleGroup langagesGroup = new ToggleGroup();
		
		menuLangue.textProperty().bind(dictionnaire.getText("langue"));
		
		menuLangue.getItems().addAll(
			langagesManager.getLangages().stream()
			.map(langage -> {
				final var item = new RadioMenuItem(langage.getName());
				item.getProperties().put("file_name", langage.getFileName());
				item.setToggleGroup(langagesGroup);
				return item;
			})
			.collect(Collectors.toList())
		);
		
		final var selected_langage = (RadioMenuItem) menuLangue.getItems().stream()
				.filter(item -> item.getProperties().get("file_name").equals(dictionnaire.getLangue().getFileName()))
				.findFirst()
				.orElse(null);
		
		if(selected_langage != null)
			selected_langage.setSelected(true);
		
		langagesGroup.selectedToggleProperty().addListener((obs, oldV, newV) -> {
			langageProperty.set(langagesManager.getLangage(newV.getProperties().get("file_name").toString()));
		});

		mainContainer.prefHeightProperty().bind(stageManager.getCurrentStage().heightProperty());
		mainContainer.prefWidthProperty().bind(stageManager.getCurrentStage().widthProperty());

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
			public String toString(final SortStrategy<JoueurFx> object) {
				return object.getLabelProperty().get();
			}

			@Override
			public SortStrategy<JoueurFx> fromString(final String string) {
				return sortSelect.getItems().stream().filter(strategy -> strategy.getLabelProperty().get().equals(string)).findFirst().orElse(null);
			}

		});
		sortSelect.setOnAction(this::sortAction);

		labelSort.textProperty().bind(dictionnaire.getText("labelSort"));

		//Scrollbar

		gridpane.add(joueursContainer, 0, 1);

		gridpane.prefWidthProperty().bind(mainContainer.prefWidthProperty());
		gridpane.prefHeightProperty().bind(mainContainer.prefHeightProperty());

		scrollpane.setFitToHeight(true);
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
				Bindings.when(((Region) (scrollpane.getContent())).heightProperty().greaterThan(gridpane.prefHeightProperty()))
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

		open_modal.translateYProperty().bind(mainContainer.prefHeightProperty().divide(2).add(-70));
		open_modal.translateXProperty().bind(mainContainer.prefWidthProperty().divide(2).add(-70));

		modalAdd.setVisible(false);
		modalAdd.prefHeightProperty().bind(gridpane.prefHeightProperty());
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
		if(openModalBinding.get()) {
			return;
		}

		modalAdd.setVisible(true);
		openTransition.play();
	}

	public void closeModal() {
		if(closeModalBinding.get()) {
			return;
		}

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
	
	private void sortAction(ActionEvent evt) {
		eventService.trigger(new EventSortSelect(sortSelect.getValue()));
	}
	
	public void setJoueurs(List<Joueur> joueurs) {
		final var task = new Task<List<CommandeAjout>>() {

			@Override
			protected List<CommandeAjout> call() throws Exception {
				final var tasks = joueurs.stream()
						.map(joueur -> new TacheCharger(joueur.getNom(), joueur.getPseudo(), joueur.getServer()))
						.collect(Collectors.toList());

				tasks.forEach(TacheCharger::run);

				return tasks.stream()
						.map(tache -> {
							try {
								return tache.get();
							} catch (InterruptedException | ExecutionException e) {
								return null;
							}
						})
						.filter(Objects::nonNull)
						.map(joueur -> new CommandeAjout(joueursContainer, joueur))
						.collect(Collectors.toList());
			}

		};
		task.setOnSucceeded(evt -> {
			Platform.runLater(() -> {
				task.getValue().forEach(commande -> gestionnaireCommandeService.addCommande(commande).executer());

				gestionnaireCommandeService.viderCommandes();

				mainContainer.getChildren().remove(loading);
				joueursContainer.setVisible(true);
			});
		});

		final var thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

}

