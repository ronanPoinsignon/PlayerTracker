package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import modele.commande.CommandeSuppression;
import modele.event.action.ActionEventSupprimer;
import modele.event.clavier.ClavierEventHandler;
import modele.event.eventaction.AddEvent;
import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import modele.joueur.Partie;
import modele.observer.ObservateurInterface;
import modele.observer.ObservateurWeb;
import modele.request.data.SummonerData;
import modele.request.data.SummonerInGame;
import service.GestionnaireCommandeService;
import service.InterfaceManager;
import service.ServiceManager;
import service.WebService;

public class ControllerPagePrincipale implements Initializable, ObservateurInterface, ObservateurWeb {

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
	private TextField nom;
	@FXML
	private TextField pseudo;

	@FXML
	private Button ajouter;
	@FXML
	private Button modifier;

	private MenuItem editItem = new MenuItem("Modifier le pseudo");
	private MenuItem removeItem = new MenuItem("Supprimer");

	final ContextMenu rowMenu = new ContextMenu();

	private SimpleObjectProperty<JoueurFx> joueurCourant = new SimpleObjectProperty<>();

	private GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);
	private InterfaceManager interfaceManager = ServiceManager.getInstance(InterfaceManager.class);
	private WebService webService = ServiceManager.getInstance(WebService.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colonneNom.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
		colonnePseudo.setCellValueFactory(cellData -> cellData.getValue().getPseudoProperty());
		colonneId.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
		colonneInGame.setCellValueFactory(cellData -> cellData.getValue().getImageConnexion());

		// Wrap de l'image dans une cellule permettant son affichage
		colonneInGame.setCellFactory(col -> {
			final var imageview = new ImageView();
			imageview.setFitHeight(20);
			imageview.setFitWidth(20);
			TableCell<JoueurFx, Image> cell = new TableCell<>() {
				@Override
				public void updateItem(Image item, boolean empty) {
					imageview.setImage(item);
				}
			};
			cell.setGraphic(imageview);
			return cell;
		});

		// clic droit sur une ligne
		table.setRowFactory(tableView -> {
			final TableRow<JoueurFx> row = new TableRow<>();
			editItem.setOnAction(event -> onEdit(row.getItem()));
			row.contextMenuProperty().bind(Bindings.when(row.emptyProperty())
					.then((ContextMenu) null)
					.otherwise(rowMenu));
			return row;
		});

		joueurCourant.addListener((obs, oldValue, newValue) -> {
			if(newValue != null) {
				modifier.setVisible(true);
				ajouter.setVisible(false);
				nom.setText(newValue.getNom());
				pseudo.setText(newValue.getPseudo());
				return;
			}
			modifier.setVisible(false);
			ajouter.setVisible(true);
		});

		nom.setAlignment(Pos.CENTER_LEFT);
		pseudo.setAlignment(Pos.CENTER_LEFT);

		rowMenu.getItems().addAll(editItem, removeItem);

		addEvent();
	}

	protected void addEvent() {
		table.addEventHandler(KeyEvent.ANY, new ClavierEventHandler(table));
		borderPane.addEventHandler(KeyEvent.ANY, new ClavierEventHandler(table));

		interfaceManager.addObs(this);
		ajouter.disableProperty().bind(interfaceManager.getDisableAjoutProperty());
		modifier.disableProperty().bind(interfaceManager.getDisableModifierProperty());
		table.disableProperty().bind(interfaceManager.getDisableTableProperty());

		removeItem.setOnAction(new ActionEventSupprimer(table));
	}

	@FXML
	public void onAjout() {
		var t = new Thread(new AddEvent(table, nom.getText(), pseudo.getText()));
		t.setDaemon(true);
		t.start();
		reset();
	}

	@FXML
	public void onModif() {
		Joueur joueur = joueurCourant.getValue();
		if(joueur == null) {
			return;
		}
		String newPseudo = pseudo.getText();
		String newNom = nom.getText();

		joueur.setNom(newNom);
		joueur.setPseudo(newPseudo);

		reset();
	}

	public void onEdit(JoueurFx joueur) {
		joueurCourant.set(joueur);
		pseudo.setDisable(true);
	}

	public void removePLayerFromTable(JoueurFx joueur) {
		gestionnaireCommandeService.addCommande(new CommandeSuppression(table, joueur)).executer();
		joueurCourant = null;
	}

	public void reset() {
		joueurCourant.set(null);
		interfaceManager.reset();
	}

	@Override
	public void notifyData(SummonerData data) {
		JoueurFx joueur = findJoueurByIdOrPseudo(data.getSummoner_id(), data.getName());
		if(joueur == null) {
			return;
		}
		joueur.setPlayerId(data.getSummoner_id());
		joueur.setPseudo(data.getName());
		joueur.setInGame(data.isIn_game());
		if(!data.isIn_game()) {
			joueur.setPartie(null);
		}
		else {
			webService.getSummonerGame(joueur.getPlayerId());
		}
	}

	@Override
	public void notifyData(SummonerInGame data) {
		JoueurFx joueur = findJoueurByIdOrPseudo(data.getSummonerId(), data.getSummonerName());
		if(joueur == null) {
			return;
		}
		joueur.setPseudo(data.getSummonerName());
		joueur.setInGame(data.isInGame());
		if(data.isInGame()) {
			joueur.setPartie(new Partie(data.getGameId(), data.getEncryptionKey()));
		}
	}

	private JoueurFx findJoueurByIdOrPseudo(String id, String pseudo) {
		List<JoueurFx> joueurs = table.getItems();
		return joueurs.stream().filter(joueur -> id != null && joueur.getPlayerId() != null && joueur.getPlayerId().equals(id)).findFirst()
				.orElse(joueurs.stream().filter(joueur -> pseudo != null && joueur.getPseudo() != null && joueur.getPseudo().equals(pseudo)).findFirst()
						.orElse(null));
	}

	@Override
	public void notifyNewStringValue(String value) {
		nom.setText("");
		pseudo.setText("");
	}
}
