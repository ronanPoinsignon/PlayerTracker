package modele.event.eventaction;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import modele.affichage.ViewElement;
import modele.joueur.JoueurFx;

public class CopierEvent extends EventAction<Void> {

	ViewElement<JoueurFx> table;

	public CopierEvent(ViewElement<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public Void execute() {
		if(table.getItems().isEmpty()) {
			return null;
		}
		var clipboard = Clipboard.getSystemClipboard();
		var content = new ClipboardContent();
		try {
			content.putString(new GetPseudoEvent(table).execute());
			clipboard.setContent(content);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			// le copier collé rate, pas grave
		}
		return null;
	}

}
