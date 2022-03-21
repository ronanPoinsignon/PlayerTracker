package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import modele.observable.Observer;
import utils.FileManager;

public class ControllerPagePrincipale implements Initializable, Observer {

	private FileManager fm = FileManager.getInstance();

	@FXML
	private TableView<Joueur> table;
	@FXML
	private TableColumn<JoueurFx, String> colonneNom;
	@FXML
	private TableColumn<JoueurFx, String> colonnePseudo;
	@FXML
	private TableColumn<JoueurFx, String> colonneId;
	@FXML
	private TableColumn<JoueurFx, Image> colonneInGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colonneNom.setCellValueFactory(cellData -> cellData.getValue().getNom());
		colonnePseudo.setCellValueFactory(cellData -> cellData.getValue().getPseudo());
		colonneId.setCellValueFactory(cellData -> cellData.getValue().getId());
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
			final TableRow<Joueur> row = new TableRow<>();
			final ContextMenu rowMenu = new ContextMenu();
			MenuItem editItem = new MenuItem("Modifier le pseudo");
			MenuItem removeItem = new MenuItem("Supprimer");
			rowMenu.getItems().addAll(editItem, removeItem);
			removeItem.setOnAction(event -> table.getItems().remove(row.getItem()));

			row.contextMenuProperty().bind(Bindings.when(row.emptyProperty())
					.then((ContextMenu) null)
					.otherwise(rowMenu));
			return row;
		});
	}

	@FXML
	public void onAjout() throws IOException {
		File fxml = fm.getFileFromResources("fxml/page_ajout.fxml");
		FXMLLoader loader = new FXMLLoader(fxml.toURI().toURL());
		Parent sceneVideo = loader.load();
		ControllerPageAjout controller = loader.getController();
		controller.addObserver(this);
		Scene scene = new Scene(sceneVideo);
		Stage stage = new Stage();
		stage.getIcons().add(fm.getImageFromResource("images/loupe.PNG"));
		stage.setTitle("Ajout d'un joueur");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void addJoueur(Joueur joueur) {
		try {
			table.getItems().add(new JoueurFx(joueur));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
