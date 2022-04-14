package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.commande.CommandeInversion;
import modele.joueur.JoueurFx;
import service.GestionnaireCommandeService;
import service.ServiceManager;

public class SwapDownEvent extends RunnableEvent {

	GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	public SwapDownEvent(TableView<JoueurFx> table) {
		super(table);
	}

	@Override
	public Void execute() {
		int index = table.getSelectionModel().getSelectedIndex();
		gestionnaireCommandeService.addCommande(new CommandeInversion(table, index, index + 1)).executer();
		return null;
	}

}
