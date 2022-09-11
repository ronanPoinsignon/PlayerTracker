package controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import modele.event.tache.event.EventEditJoueurClick;
import modele.event.tache.event.EventJoueurEdited;
import modele.joueur.Joueur;
import modele.joueur.JoueurFx;
import service.DictionnaireService;
import service.EventService;
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

	@FXML
	private ImageView imageChampion;

	@FXML
	private Label labelChampion;

	@FXML
	private Label labelGameType;

	@FXML
	private Label labelDuree;

	private final BooleanProperty isConnecte = new SimpleBooleanProperty();
	private final ObjectProperty<Image> imageChampionProperty = new SimpleObjectProperty<>();

	private final FileManager fm = ServiceManager.getInstance(FileManager.class);
	private final EventService eventService = ServiceManager.getInstance(EventService.class);
	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private static final int ICON_SIZE = 20;

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

		delete.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if(!MouseButton.PRIMARY.equals(event.getButton())) {
				event.consume();
			}
		});
		delete.setOnMouseClicked(event -> {
			pane.fireEvent(event);
			new MouseEventSuppression((PaneViewJoueurFx) pane.getParent()).handle(event);
		});

		spectate.addEventFilter(MouseEvent.MOUSE_CLICKED,event -> {
			if(!MouseButton.PRIMARY.equals(event.getButton())) {
				event.consume();
			}
		});
		spectate.setOnMouseClicked(event -> {
			pane.fireEvent(event);
			new MouseEventRegarder((PaneViewJoueurFx) pane.getParent()).handle(event);
		});

		edit.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if(!MouseButton.PRIMARY.equals(event.getButton())) {
				event.consume();
			}
		});
		edit.setOnMouseClicked(event -> {
			pane.fireEvent(event);
			eventService.trigger(new EventEditJoueurClick(element.get()));
		});

		eventService.addListener(EventJoueurEdited.EVENT_JOUEUR_EDITED, event -> {
			sortPaneView();
		});

		imageChampion.imageProperty().bind(imageChampionProperty);
		statut.textProperty().bind(Bindings.when(isConnecte).then(dictionnaire.getText("online")).otherwise(dictionnaire.getText("offline")));
		spectate.disableProperty().bind(isConnecte.not());

		nom.setMaxWidth(pane.getPrefWidth() - nom.getLayoutX() - 80);
		pseudo.setMaxWidth(pane.getPrefWidth() - pseudo.getLayoutX() - 80);

		isConnecte.addListener((obs, oldV, newV) -> {
			if(element.getValue() == null) {
				return;
			}

			Image tempImage = null;

			if(element.getValue().isInGame()) {
				tempImage = decodeImage(element.get().getPartie().getChampion().getBase64ChampionImage());
			}

			final var image = tempImage;
			Platform.runLater(() ->	{
				imageChampionProperty.setValue(element.get().isInGame() ? image : null);

				if(element.get().isInGame()) {
					labelChampion.setText(element.get().getPartie().getChampion().getChampionName());
					labelGameType.setText(element.get().getPartie().getGameType());
				}
				else {
					labelDuree.setText("");
					labelChampion.setText("");
					labelGameType.setText("");
				}
			});

			sortPaneView();
		});

		scheduler.scheduleWithFixedDelay(() -> {
			if(!isConnecte.get()) {
				return;
			}

			Platform.runLater(() -> {
				if(element.get().getPartie().getStartTime() == 0) {
					labelDuree.setText("");
				}
				else {
					labelDuree.setText(getDuree(element.get()));
				}
			});
		}, 0, 1, TimeUnit.SECONDS);
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
			imageJoueur.setImage(decodeImage(newV.getBase64Icon()));
		};
	}

	public String getDuree(final Joueur joueur) {
		if(joueur == null) {
			return "";
		}

		final var partie = joueur.getPartie();
		if(partie == null) {
			return "";
		}

		final var diff = System.currentTimeMillis() - partie.getStartTime();
		final var duration = Duration.ofMillis(diff);

		return String.format("%02d:%02d", duration.toMinutes(), duration.toSecondsPart());
	}

	public void sortPaneView() {
		final var paneView = (PaneViewJoueurFx) pane.getParent();

		if(paneView == null) {
			return;
		}

		paneView.updateSort();
	}

	private Image decodeImage(final String base64encoded) {
		if(base64encoded == null) {
			return null;
		}

		final var decoder = Base64.getDecoder();
		final var imageBytes = decoder.decode(base64encoded);
		final var bis = new ByteArrayInputStream(imageBytes);
		return new Image(bis);
	}
}
