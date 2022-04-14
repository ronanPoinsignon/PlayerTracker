package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.commande.CommandeInversion;
import modele.joueur.JoueurFx;

public class SwapUpEvent extends SwapDownEvent {

	public SwapUpEvent(TableView<JoueurFx> table) {
		super(table);
	}

	@Override
	public void run() {
		int index = table.getSelectionModel().getSelectedIndex();
		gestionnaireCommandeService.addCommande(new CommandeInversion(table, index, index - 1)).executer();
	}

}
