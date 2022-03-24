package controller;

import java.io.IOException;
import java.net.URL;
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
import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import service.NotificationService;
import service.Service;

public class ControllerPagePrincipale implements Initializable {

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

	private SimpleObjectProperty<JoueurFx> joueurCourant = new SimpleObjectProperty<>();

	private NotificationService notificationService = Service.getInstance(NotificationService.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colonneNom.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
		colonnePseudo.setCellValueFactory(cellData -> cellData.getValue().getPseudoProperty());
		colonneId.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
		colonneInGame.setCellValueFactory(cellData -> cellData.getValue().getImageConnexion());

		// Wrap de l'image dans une cellule permettant son affichage
		colonneInGame.setCellFactory(col -> {
			final ImageView imageview = new ImageView();
			imageview.setFitHeight(20);
			imageview.setFitWidth(20);
			TableCell<JoueurFx, Image> cell = new TableCell<JoueurFx, Image>() {
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
			final ContextMenu rowMenu = new ContextMenu();
			MenuItem editItem = new MenuItem("Modifier le pseudo");
			MenuItem removeItem = new MenuItem("Supprimer");
			rowMenu.getItems().addAll(editItem, removeItem);
			removeItem.setOnAction(event -> {
				JoueurFx joueur = row.getItem();
				table.getItems().remove(joueur);
				notificationService.unbind(joueur);
				reset();
			});
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
	}

	@FXML
	public void onAjout() throws IOException {
		String pseudoStr = pseudo.getText();
		if(pseudoStr == null || pseudoStr.trim().isEmpty()) {
			return;
		}
		JoueurFx joueurFx = new JoueurFx(new Joueur(nom.getText(), pseudo.getText()));
		table.getItems().add(joueurFx);
		notificationService.bind(joueurFx);
		Thread t = new Thread(() -> setId(joueurFx));
		t.setDaemon(true);
		t.start();
		reset();
	}

	private void setId(Joueur joueur) {
		joueur.setId("id test");
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

	private void reset() {
		pseudo.setDisable(false);
		nom.setDisable(false);
		pseudo.setText("");
		nom.setText("");
		joueurCourant.set(null);
	}
}
