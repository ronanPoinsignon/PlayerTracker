package modele.event.action;

import javafx.event.ActionEvent;
import modele.joueur.Joueur;
import service.AlertFxService;
import service.FileManager;
import service.ServiceManager;

public class ActionEventRegarder extends ActionEventHandler {

	AlertFxService alerteService = ServiceManager.getInstance(AlertFxService.class);
	FileManager fm = ServiceManager.getInstance(FileManager.class);

	private Joueur joueur;

	public ActionEventRegarder(Joueur joueur) {
		this.joueur = joueur;
	}

	@Override
	public void handle(ActionEvent event) {
		if(joueur == null) {
			return;
		}
		var partie = joueur.getPartie();
		if(partie == null) {
		}
	}

}
