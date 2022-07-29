package modele.event.eventaction;

import javafx.scene.input.Clipboard;
import modele.affichage.ViewElement;
import modele.joueur.JoueurFx;
import service.ServerManager;
import service.ServiceManager;

public class CollerEvent extends EventAction<Void> {

	ServerManager sm = ServiceManager.getInstance(ServerManager.class);
	ViewElement<JoueurFx> table;

	public CollerEvent(ViewElement<JoueurFx> table) {
		this.table = table;
	}

	@Override
	public Void execute() {
		final var clipboard = Clipboard.getSystemClipboard();
		final var pseudo = clipboard.getString();
		if(pseudo != null && !pseudo.isEmpty()) {
			final var th = new Thread(new AddEvent(table, "", pseudo, sm.getDefaultServer()));
			th.setDaemon(true);
			th.start();
		}
		return null;
	}

}