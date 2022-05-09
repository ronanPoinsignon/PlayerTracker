package modele.event.eventaction;

import modele.affichage.ViewElement;
import modele.commande.CommandeInversion;

public class SwapUpEvent<T> extends SwapDownEvent<T> {

	public SwapUpEvent(ViewElement<T> table) {
		super(table);
	}

	@Override
	public Void execute() {
		int index = table.getSelectedIndex();
		if(index == 0) {
			return null;
		}
		gestionnaireCommandeService.addCommande(new CommandeInversion<>(table, index, index - 1)).executer();
		return null;
	}

}
