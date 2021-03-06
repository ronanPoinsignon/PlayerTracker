package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.commande.CommandeInversion;

public class SwapUpEvent<T> extends SwapDownEvent<T> {

	public SwapUpEvent(final TableView<T> table) {
		super(table);
	}

	@Override
	public Void execute() {
		final var index = table.getSelectionModel().getSelectedIndex();
		if(index == 0) {
			return null;
		}
		gestionnaireCommandeService.addCommande(new CommandeInversion<>(table, index, index - 1)).executer();
		return null;
	}

}