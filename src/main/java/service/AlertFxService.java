package service;

import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	public void alert(final Exception exception) {
		try {
			throw exception;
		}
		catch(AException | ARuntimeException e) {
			showWarningAlert(e);
		}
		catch (final Exception e) {
			showAlertFrom(e);
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

	private void showAlertFrom(final Exception e) {
		e.printStackTrace();
		Platform.runLater(() -> {
			final var header = dictionnaire.getText("showAlertFromHeader").getValue();
			final var context = dictionnaire.getText("showAlertFromContext").getValue();
			final var alert = createErrorAlert(header, context);
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
		alert.setContentText(contexte);
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