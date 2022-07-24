package modele.event.eventaction;

import service.GestionnaireCommandeService;
import service.ServerManager;
import service.ServiceManager;

public class AnnulerEvent extends RunnableEvent {

	ServerManager sm = ServiceManager.getInstance(ServerManager.class);
	GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	@Override
	public Void execute() {
		gestionnaireCommandeService.annuler();
		return null;
	}
}
