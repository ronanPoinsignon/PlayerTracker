package modele.event.eventaction;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;

public class GetPseudoEvent extends EventAction<String> {

	TableView<JoueurFx> table;

	public GetPseudoEvent(final TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public String execute() {
		final var index = table.getSelectionModel().getSelectedIndex();
		return table.getItems().get(index).getPseudo();
	}

}