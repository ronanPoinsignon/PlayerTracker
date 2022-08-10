package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import modele.commande.CommandeAjout;
import modele.commande.CommandeModifier;
import modele.event.action.ActionEventRegarder;
import modele.event.action.ActionEventSupprimer;
import modele.event.clavier.ClavierEventHandler;
import modele.event.eventaction.AddEvent;
import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;
import modele.observer.ObservateurInterface;
import service.AlertFxService;
import service.DictionnaireService;
import service.GestionnaireCommandeService;
import service.InterfaceManager;
import service.LoadService;
import service.ServerManager;
import service.ServiceManager;
import service.exception.SauvegardeCorrompueException;

public class ControllerPagePrincipale implements Initializable, ObservateurInterface {

	@FXML
	private BorderPane borderPane;

	@FXML
	private TableView<JoueurFx> table;
	@FXML
	private TableColumn<JoueurFx, String> colonneNom;
	@FXML
	private TableColumn<JoueurFx, String> colonnePseudo;
	@FXML
	private TableColumn<JoueurFx, String> colonneId;
	@FXML
	private TableColumn<JoueurFx, Image> colonneInGame;
	@FXML
	private TableColumn<JoueurFx, String> colonneServeur;
	@FXML
	private TableColumn<JoueurFx, String> colonneGameType;

	@FXML
	private TextField nom;
	@FXML
	private TextField pseudo;
	@FXML
	private ComboBox<Serveur> serverList;

	@FXML
	private Button ajouter;
	@FXML
	private Button modifier;

	private final MenuItem editItem = new MenuItem();
	private final MenuItem removeItem = new MenuItem();
	private final MenuItem lookItem = new MenuItem();

	final ContextMenu rowMenu = new ContextMenu();

	private final SimpleObjectProperty<JoueurFx> joueurCourant = new SimpleObjectProperty<>();

	private final GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);
	private final InterfaceManager interfaceManager = ServiceManager.getInstance(InterfaceManager.class);
	private final LoadService loadService = ServiceManager.getInstance(LoadService.class);
	private final ServerManager serverManager = ServiceManager.getInstance(ServerManager.class);
	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	private final AlertFxService alerteService = ServiceManager.getInstance(AlertFxService.class);

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		List<Joueur> joueurs = null;
		try {
			joueurs = loadService.load();
		} catch (final SauvegardeCorrompueException e) {
			alerteService.alert(e);
			joueurs = new ArrayList<>();
		} catch (final IOException e) {
			alerteService.alert(e);
			return;
		}

		joueurs.stream().map(JoueurFx::new)
		.map(joueur -> new CommandeAjout(table, joueur))
		.forEach(commande -> gestionnaireCommandeService.addCommande(commande).executer());
		gestionnaireCommandeService.viderCommandes();

		colonneNom.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
		colonnePseudo.setCellValueFactory(cellData -> cellData.getValue().getPseudoProperty());
		colonneId.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
		colonneInGame.setCellValueFactory(cellData -> cellData.getValue().getImageConnexion());
		colonneServeur.setCellValueFactory(cellData -> cellData.getValue().getServerNameProperty());
		colonneGameType.setCellValueFactory(cellData -> cellData.getValue().getGameTypeProperty());

		// Wrap de l'image dans une cellule permettant son affichage
		colonneInGame.setCellFactory(col -> {
			final var imageview = new ImageView();
			imageview.setFitHeight(20);
			imageview.setFitWidth(20);
			final TableCell<JoueurFx, Image> cell = new TableCell<>() {
				@Override
				public void updateItem(final Image item, final boolean empty) {
					imageview.setImage(item);
				}
			};
			cell.setGraphic(imageview);
			return cell;
		});

		serverList.setConverter(new StringConverter<Serveur>() {
			@Override
			public String toString(final Serveur object) {
				return object.getLabel();
			}
			@Override
			public Serveur fromString(final String string) {
				return serverList.getItems().stream().filter(ap ->
				ap.getServerId().equals(string)).findFirst().orElse(null);
			}
		});

		// clic droit sur une ligne
		table.setRowFactory(tableView -> {
			final var row = new TableRow<JoueurFx>();
			editItem.setOnAction(event -> onEdit(table.getSelectionModel().getSelectedItem()));
			row.contextMenuProperty().bind(Bindings.when(row.emptyProperty())
					.then((ContextMenu) null)
					.otherwise(rowMenu));
			return row;
		});

		joueurCourant.addListener((obs, oldValue, newValue) -> {
			if(newValue == null) {
				return;
			}
			interfaceManager.setDisableModifierProperty(false);
			interfaceManager.setDisableAjoutProperty(true);
			interfaceManager.setNomValue(newValue.getNom());
			interfaceManager.setPseudoValue(newValue.getPseudo());
			interfaceManager.setDisablePseudoProperty(true);
			interfaceManager.setVisibleModifierProperty();
			interfaceManager.setServerValue(newValue.getServer());
			interfaceManager.setDisableServerProperty(true);
		});

		nom.setAlignment(Pos.CENTER_LEFT);
		pseudo.setAlignment(Pos.CENTER_LEFT);

		rowMenu.getItems().addAll(editItem, removeItem, lookItem);

		serverList.getItems().addAll(serverManager.getServers());
		serverList.setValue(serverManager.getDefaultServer());
		serverList.getItems().sort(Comparator.comparing(Serveur::getServerId));

		addEvent();
	}

	protected void addEvent() {
		borderPane.addEventHandler(KeyEvent.KEY_PRESSED, new ClavierEventHandler(table));

		interfaceManager.addObs(this);

		ajouter.disableProperty().bind(interfaceManager.getDisableAjoutProperty());
		ajouter.visibleProperty().bind(interfaceManager.getVisibleAjoutProperty());

		modifier.disableProperty().bind(interfaceManager.getDisableModifierProperty());
		modifier.visibleProperty().bind(interfaceManager.getVisibleModifierProperty());

		table.disableProperty().bind(interfaceManager.getDisableTableProperty());

		interfaceManager.getDisablePseudoProperty().addListener((obs, oldV, newV) -> pseudo.setDisable(newV));
		interfaceManager.getDisableNomProperty().addListener((obs, oldV, newV) -> nom.setDisable(newV));
		interfaceManager.getDisableServerProperty().addListener((obs, oldV, newV) -> serverList.setDisable(newV));

		pseudo.setOnAction(evt -> {
			evt.consume();
			onAjout();
		});
		nom.setOnAction(evt -> {
			evt.consume();
			pseudo.requestFocus();
		});

		removeItem.setOnAction(new ActionEventSupprimer(table));

		table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
			lookItem.visibleProperty().unbind();
			if(newV == null) {
				lookItem.setVisible(false);
				return;
			}
			lookItem.visibleProperty().bind(newV.getIsConnecteProperty());
			lookItem.setOnAction(new ActionEventRegarder(newV));
		});

		colonneNom.textProperty().bind(dictionnaire.getText("colonneNomLegende"));
		colonnePseudo.textProperty().bind(dictionnaire.getText("colonnePseudoLegende"));
		colonneId.textProperty().bind(dictionnaire.getText("colonneIdLegende"));
		colonneInGame.textProperty().bind(dictionnaire.getText("colonneInGameLegende"));
		colonneServeur.textProperty().bind(dictionnaire.getText("colonneServeurLegende"));
		colonneGameType.textProperty().bind(dictionnaire.getText("colonneGameTypeLegend"));

		ajouter.textProperty().bind(dictionnaire.getText("menuItemAjouter"));
		modifier.textProperty().bind(dictionnaire.getText("menuItemModifier"));
		editItem.textProperty().bind(dictionnaire.getText("menuItemModifier"));
		removeItem.textProperty().bind(dictionnaire.getText("menuItemSupprimer"));
		lookItem.textProperty().bind(dictionnaire.getText("menuItemRegarder"));
		nom.promptTextProperty().bind(dictionnaire.getText("nomPlaceHolder"));
		pseudo.promptTextProperty().bind(dictionnaire.getText("pseudoPlaceHolder"));
	}

	@FXML
	public void onAjout() {
		final var t = new Thread(new AddEvent(table, nom.getText(), pseudo.getText(), serverList.getValue()));
		t.setDaemon(true);
		t.start();
		reset();
	}

	@FXML
	public void onModif() {
		final Joueur joueur = joueurCourant.getValue();
		if(joueur == null) {
			return;
		}
		final var newNom = nom.getText();
		final var newPseudo = pseudo.getText();
		final var newServer = serverList.getValue();

		gestionnaireCommandeService.addCommande(new CommandeModifier(joueur, newNom, newPseudo, newServer)).executer();

		reset();
	}

	public void onEdit(final JoueurFx joueur) {
		joueurCourant.set(joueur);
	}

	public void reset() {
		joueurCourant.set(null);
		interfaceManager.reset();
	}

	@Override
	public void notifyNewStringValueNom(final String value) {
		nom.setText(value);
	}

	@Override
	public void notifyNewStringValuePseudo(final String value) {
		pseudo.setText(value);
	}

	@Override
	public void notifyNewServerValue(final Serveur value) {
		serverList.setValue(value);
	}
}
