package service;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import modele.exception.AException;
import modele.exception.ARuntimeException;
import modele.exception.IException;
import modele.joueur.Joueur;

public class AlertFxService implements IService {

	TrayIconService trayIconService;

	public void alert(Exception exception) {
		try {
			throw exception;
		}
		catch(AException | ARuntimeException e) {
			showWarningAlert(e);
		}
		catch (Exception e) {
			showAlertFrom(e);
		}
	}

	private void showWarningAlert(IException e) {
		Platform.runLater(() -> {
			var alert = new Alert(AlertType.WARNING);
			alert.setTitle("PlayerTracker");
			alert.setHeaderText(e.getMessage());
			alert.setContentText(e.getDescription());

			alert.showAndWait();
			trayIconService.quitter();
		});
	}

	private void showAlertFrom(Exception e) {
		Platform.runLater(() -> {
			var alert = new Alert(AlertType.ERROR);
			alert.setTitle("PlayerTracker");
			alert.setHeaderText("Une erreur s'est produite.");
			alert.setContentText("Veuillez redémarrer l'application.");

			alert.showAndWait();
			trayIconService.quitter();
		});
	}

	/**
	 * Affiche une {@link Alert} pour avertir l'utilisateur que les joueurs
	 * qu'il souhaite ajouter à la liste sont déjà présents.
	 * @param listeJoueurs
	 */
	public void showWarningAlertJoueursDejaPresents(List<Joueur> listeJoueurs) {
		if(listeJoueurs.isEmpty()) {
			return;
		}
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Joueurs déjà présents");

			alert.setHeaderText("Certains joueurs sont déjà présents dans la liste");
			StringBuilder liste = new StringBuilder("Les joueurs suivants sont déjà présents :");
			for(Joueur video : listeJoueurs) {
				liste.append("\n\t- " + video.getPseudo());
			}

			alert.setContentText(liste.toString());

			alert.showAndWait();
		});
	}

	@Override
	public void init() {
		trayIconService = ServiceManager.getInstance(TrayIconService.class);
	}
}
