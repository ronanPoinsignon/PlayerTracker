package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.commande.CommandeInversion;
import service.GestionnaireCommandeService;
import service.ServiceManager;

public class SwapDownEvent<T> extends RunnableEvent<T> {

	GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	public SwapDownEvent(final TableView<T> table) {
		super(table);
	}

	@Override
	public Void execute() {
		final var index = table.getSelectionModel().getSelectedIndex();
		if(table.getItems().size() == index + 1) {
			return null;
		}
		gestionnaireCommandeService.addCommande(new CommandeInversion<>(table, index, index + 1)).executer();
		return null;
	}

}