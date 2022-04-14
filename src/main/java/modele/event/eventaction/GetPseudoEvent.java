package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;

public class GetPseudoEvent extends EventAction<String> {

	TableView<JoueurFx> table;

	public GetPseudoEvent(TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public String execute() {
		int index = table.getSelectionModel().getSelectedIndex();
		return table.getItems().get(index).getPseudo();
	}

}
