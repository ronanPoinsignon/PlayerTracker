package service;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import modele.joueur.Joueur;
import modele.web.request.DataNotFoundException;

public class AlertFxService implements IService {

	public void alert(Exception exception) {
		try {
			throw exception;
		}
		catch(DataNotFoundException e) {
			showAlertFrom(e);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Affiche une {@link Alert} pour avertir l'utilisateur que le joueur donné
	 * à l'application n'existe pas.
	 */
	public void showAlertFrom(DataNotFoundException e) {
		Platform.runLater(() -> {
			var alert = new Alert(AlertType.WARNING);
			alert.setTitle("Joueur non trouvé");
			alert.setHeaderText("Le joueur n'existe pas");
			alert.setContentText("Le joueur donné n'existe pas.");

			alert.showAndWait();
		});
	}

	public void showAlertFrom(Throwable e) {
		System.out.println("erreur :");
		e.printStackTrace();
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
}
