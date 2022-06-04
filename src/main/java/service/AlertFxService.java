package service;

import java.util.List;

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
			final var alert = new Alert(AlertType.WARNING);
			alert.setTitle("PlayerTracker");
			alert.setHeaderText(e.getMessage());
			alert.setContentText(e.getDescription());
			final var stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(fm.getImageFromResource("images/loupe.PNG"));

			alert.showAndWait();
		});
	}

	private void showAlertFrom(final Exception e) {
		Platform.runLater(() -> {
			final var alert = new Alert(AlertType.ERROR);
			alert.setTitle("PlayerTracker");
			alert.setHeaderText("Une erreur s'est produite.");
			alert.setContentText("Veuillez redémarrer l'application.");
			final var stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(fm.getImageFromResource("images/loupe.PNG"));

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
			final var alert = new Alert(AlertType.WARNING);
			alert.setTitle("Joueurs déjà présents");

			alert.setHeaderText("Certains joueurs sont déjà présents dans la liste");
			final var liste = new StringBuilder("Les joueurs suivants sont déjà présents :");
			for(final Joueur video : listeJoueurs) {
				liste.append("\n\t- " + video.getPseudo());
			}

			alert.setContentText(liste.toString());

			alert.showAndWait();
		});
	}

	@Override
	public void init() {
		trayIconService = ServiceManager.getInstance(TrayIconService.class);
		fm = ServiceManager.getInstance(FileManager.class);
	}
}