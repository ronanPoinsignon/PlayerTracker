package modele.event.action;

import javafx.event.ActionEvent;
import service.GestionnaireCommandeService;
import service.ServiceManager;

/**
 * Evénement d'annulation d'une action.
 * @author ronan
 *
 */
public class ActionEventAnnuler extends ActionEventHandler {

	GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	@Override
	public void handle(ActionEvent event) {
		gestionnaireCommandeService.annuler();
	}

}
