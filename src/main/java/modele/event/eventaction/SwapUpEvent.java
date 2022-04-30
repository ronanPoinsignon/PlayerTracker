package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.commande.CommandeInversion;
import modele.joueur.JoueurFx;

public class SwapUpEvent extends SwapDownEvent {

	public SwapUpEvent(TableView<JoueurFx> table) {
		super(table);
	}

	@Override
	public Void execute() {
		int index = table.getSelectionModel().getSelectedIndex();
		if(index == 0) {
			return null;
		}
		gestionnaireCommandeService.addCommande(new CommandeInversion(table, index, index - 1)).executer();
		return null;
	}

}
