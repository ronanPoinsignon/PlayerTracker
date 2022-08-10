package controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import modele.affichage.PaneViewJoueurFx;
import modele.event.mouse.MouseEventRegarder;
import modele.event.mouse.MouseEventSuppression;
import modele.joueur.JoueurFx;
import service.FileManager;
import service.ServiceManager;

public class JoueurController extends ElementController<JoueurFx> implements Initializable {

	@FXML
	private Pane pane;

	@FXML
	private Label nom;

	@FXML
	private Label pseudo;

	@FXML
	private Label serveur;

	@FXML
	private ImageView imageJoueur;

	@FXML
	private Label statut;

	@FXML
	private ImageView imageStatut;

	@FXML
	private GridPane buttons;

	@FXML
	private Button spectate;

	@FXML
	private Button edit;

	@FXML
	private Button delete;

	private final BooleanProperty isConnecte = new SimpleBooleanProperty();
	private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

	private final FileManager fm = ServiceManager.getInstance(FileManager.class);

	private static final int ICON_SIZE = 24;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		final var viewImage = new ImageView(fm.getImageFromResource("images/view.png"));
		final var editImage = new ImageView(fm.getImageFromResource("images/edit.png"));
		final var deleteImage = new ImageView(fm.getImageFromResource("images/delete.png"));

		viewImage.setFitWidth(JoueurController.ICON_SIZE);
		viewImage.setFitHeight(JoueurController.ICON_SIZE);
		editImage.setFitWidth(JoueurController.ICON_SIZE);
		editImage.setFitHeight(JoueurController.ICON_SIZE);
		deleteImage.setFitWidth(JoueurController.ICON_SIZE);
		deleteImage.setFitHeight(JoueurController.ICON_SIZE);

		spectate.setGraphic(viewImage);
		edit.setGraphic(editImage);
		delete.setGraphic(deleteImage);

		delete.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> MouseButton.PRIMARY.equals(event.getButton()));
		delete.setOnMouseClicked(event -> {
			pane.fireEvent(event);
			new MouseEventSuppression((PaneViewJoueurFx) pane.getParent()).handle(event);
		});

		spectate.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> MouseButton.PRIMARY.equals(event.getButton()));
		spectate.setOnMouseClicked(event -> {
			pane.fireEvent(event);
			new MouseEventRegarder((PaneViewJoueurFx) pane.getParent()).handle(event);
		});

		imageJoueur.imageProperty().bind(imageProperty);
		statut.textProperty().bind(Bindings.when(isConnecte).then("En jeu").otherwise("Déconnecté"));
		spectate.disableProperty().bind(isConnecte.not());

		nom.setMaxWidth(pane.getPrefWidth() - nom.getLayoutX() - 80);
		pseudo.setMaxWidth(pane.getPrefWidth() - pseudo.getLayoutX() - 80);

		isConnecte.addListener((obs, oldV, newV) -> {
			if(element.getValue() == null) {
				return;
			}

			Image tempImage = null;

			if(element.getValue().isInGame()) {
				final var decoder = Base64.getDecoder();
				final var imageBytes = decoder.decode(element.get().getPartie().getChampion().getBase64ChampionImage());
				final var bis = new ByteArrayInputStream(imageBytes);
				tempImage = new Image(bis);
			}

			final var image = tempImage;
			Platform.runLater(() ->	{
				imageProperty.setValue(element.getValue().isInGame() ? image : null);
			});
		});
	}

	@Override
	public ChangeListener<JoueurFx> getOnChangeListenerEvent() {
		return (obs, oldV, newV) -> {
			isConnecte.unbind();
			imageStatut.imageProperty().unbind();
			nom.textProperty().unbind();

			isConnecte.bind(newV.getIsConnecteProperty());
			imageStatut.imageProperty().bind(newV.getImageConnexion());

			nom.textProperty().bind(newV.getNomProperty());
			pseudo.textProperty().bind(newV.getPseudoProperty());
			serveur.textProperty().bind(Bindings.when(newV.getServerProperty().isNotNull()).then(newV.getServer().getLabel()).otherwise(""));
		};
	}

}
