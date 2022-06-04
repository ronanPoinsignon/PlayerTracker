package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.commande.CommandeSuppression;
import modele.joueur.JoueurFx;
import service.GestionnaireCommandeService;
import service.ServiceManager;

public class DeleteEvent extends RunnableEvent<JoueurFx> {

	private final GestionnaireCommandeService gestionnaireCommandeService = ServiceManager.getInstance(GestionnaireCommandeService.class);

	public DeleteEvent(final TableView<JoueurFx> table) {
		super(table);
	}

	@Override
	public Void execute() {
		final var selectedIndex = table.getSelectionModel().getSelectedIndex();
		if (selectedIndex < 0) {
			return null;
		}
		final var element = table.getItems().get(selectedIndex);
		gestionnaireCommandeService.addCommande(new CommandeSuppression(table, element)).executer();
		return null;
	}

}