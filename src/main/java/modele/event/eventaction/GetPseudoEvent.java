package modele.event.eventaction;

import modele.affichage.ViewElement;
import modele.joueur.JoueurFx;

public class GetPseudoEvent extends EventAction<String> {

	ViewElement<JoueurFx> table;

	public GetPseudoEvent(ViewElement<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public String execute() {
		int index = table.getSelectedIndex();
		return table.getItems().get(index).getPseudo();
	}

}