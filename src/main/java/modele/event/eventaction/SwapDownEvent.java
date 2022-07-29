package modele.event.eventaction;

import modele.affichage.ViewElement;
import modele.commande.CommandeInversion;
import service.GestionnaireCommandeService;
import service.ServiceManager;

public class SwapDownEvent<T> extends RunnableEventWithTable<T> {

	GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	public SwapDownEvent(ViewElement<T> table) {
		super(table);
	}

	@Override
	public Void execute() {
		int index = table.getSelectedIndex();
		if(table.getItems().size() == index + 1) {
			return null;
		}
		gestionnaireCommandeService.addCommande(new CommandeInversion<>(table, index, index + 1)).executer();
		return null;
	}

}