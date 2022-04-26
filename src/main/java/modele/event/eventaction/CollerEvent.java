package modele.event.eventaction;

import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import modele.joueur.JoueurFx;

public class CollerEvent extends EventAction<Void> {

	TableView<JoueurFx> table;

	public CollerEvent(TableView<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public Void execute() {
		var clipboard = Clipboard.getSystemClipboard();
		var pseudo = clipboard.getString();
		if(pseudo != null && !pseudo.isEmpty()) {
			var th = new Thread(new AddEvent(table, "", pseudo));
			th.setDaemon(true);
			th.start();
		}
		return null;
	}

}
