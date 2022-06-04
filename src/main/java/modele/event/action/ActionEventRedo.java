package modele.event.action;

import javafx.event.ActionEvent;
import service.GestionnaireCommandeService;
import service.ServiceManager;

/**
 * Evénement refaisant l'action annulée.
 * @author ronan
 *
 */
public class ActionEventRedo extends ActionEventHandler {

	GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	@Override
	public void handle(final ActionEvent event) {
		gestionnaireCommandeService.reexecuter();
	}

}