package modele.event.eventaction;

import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import modele.joueur.JoueurFx;

public class CopierEvent extends EventAction<Void> {

	TableView<JoueurFx> table;

	public CopierEvent(final TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public Void execute() {
		if(table.getItems().isEmpty()) {
			return null;
		}
		final var clipboard = Clipboard.getSystemClipboard();
		final var content = new ClipboardContent();
		try {
			content.putString(new GetPseudoEvent(table).execute());
			clipboard.setContent(content);
		}
		catch(final ArrayIndexOutOfBoundsException e) {
			// le copier collé rate, pas grave
		}
		return null;
	}

}