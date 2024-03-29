package service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import modele.exception.AException;
import modele.exception.ARuntimeException;
import modele.exception.IException;
import modele.joueur.Joueur;

public class AlertFxService implements IService {

	TrayIconService trayIconService;
	FileManager fm;
	PropertiesService ps;
	DictionnaireService dictionnaire;

	public void alert(final Throwable exception) {
		try {
			throw exception;
		}
		catch(AException | ARuntimeException e) {
			showWarningAlert(e);
		}
		catch (final Throwable e) {
			showErrorAlert(e);
		}
	}

	private void showWarningAlert(final IException e) {
		Platform.runLater(() -> {
			createWarningAlert(e.getMessageError(), e.getDescription()).showAndWait();
			final var t = new Thread(e.next());
			t.setDaemon(true);
			t.start();
		});
	}

	private void showErrorAlert(final Throwable e) {
		Platform.runLater(() -> {
			final var alert = createErrorAlert("Un problème est apparu", "Veuillez redémarrer l'application");

			final var sw = new StringWriter();
			final var pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			final var exceptionText = sw.toString();

			final var label = new Label("Erreur : ");

			final var textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			final var expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			alert.getDialogPane().setExpandableContent(expContent);

			alert.showAndWait();
			trayIconService.quitter();
		});
	}

	/**
	 * Affiche une {@link Alert} pour avertir l'utilisateur que les joueurs
	 * qu'il souhaite ajouter à la liste sont déjà présents.
	 * @param listeJoueurs
	 */
	public void showWarningAlertJoueursDejaPresents(final List<Joueur> listeJoueurs) {
		if(listeJoueurs.isEmpty()) {
			return;
		}
		Platform.runLater(() -> {
			final var header = dictionnaire.getText("showWarningAlertJoueursDejaPresentsHeader").getValue();
			final var liste = new StringBuilder(dictionnaire.getText("showWarningAlertJoueursDejaPresentsListe") + " :\n\t");
			final var joueurs = listeJoueurs.stream().map(Joueur::getPseudo).collect(Collectors.joining("\n\t- "));
			liste.append(joueurs);
			final var alert = createWarningAlert(header, liste.toString());
			alert.showAndWait();
		});
	}

	private Alert createWarningAlert(final String header, final String contexte) {
		return createAlert(AlertType.WARNING, header, contexte);
	}

	private Alert createErrorAlert(final String header, final String contexte) {
		return createAlert(AlertType.ERROR, header, contexte);
	}

	private Alert createAlert(final AlertType type, final String header, final String contexte) {
		final var alert = new Alert(type);
		alert.setTitle(ps.get("application_name"));
		alert.setHeaderText(header);
		final var label = new Label(contexte);
		alert.getDialogPane().setContent(label);
		final var stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(fm.getImageFromResource("images/icon.png"));
		return alert;
	}

	@Override
	public void init() {
		trayIconService = ServiceManager.getInstance(TrayIconService.class);
		fm = ServiceManager.getInstance(FileManager.class);
		ps = ServiceManager.getInstance(PropertiesService.class);
		dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	}
}