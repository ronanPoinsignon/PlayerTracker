package modele.event.eventaction;

import modele.affichage.ViewElement;
import modele.commande.CommandeSuppression;
import modele.joueur.JoueurFx;
import service.GestionnaireCommandeService;
import service.ServiceManager;

public class DeleteEvent extends RunnableEventWithTable<JoueurFx> {

	private final GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	public DeleteEvent(ViewElement<JoueurFx> table) {
		super(table);
	}

	@Override
	public Void execute() {
		int selectedIndex = table.getSelectedIndex();
		if (selectedIndex < 0) {
			return null;
		}
		final var element = table.getItems().get(selectedIndex);
		gestionnaireCommandeService.addCommande(new CommandeSuppression(table, element)).executer();
		return null;
	}

}