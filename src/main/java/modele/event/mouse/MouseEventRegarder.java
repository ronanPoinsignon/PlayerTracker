package modele.event.mouse;

import javafx.scene.input.MouseEvent;
import modele.affichage.ViewElement;
import modele.event.eventaction.RegarderEvent;
import modele.joueur.JoueurFx;

public class MouseEventRegarder extends MouseEventHandler {

	public MouseEventRegarder(final ViewElement<JoueurFx> table) {
		super(table);
	}

	@Override
	public void handle(final MouseEvent event) {
		final var selectedIndex = table.getSelectedIndex();
		if (selectedIndex < 0) {
			return;
		}
		final var element = table.getItems().get(selectedIndex);

		final var thread = new Thread(new RegarderEvent(element));
		thread.setDaemon(true);
		thread.start();
	}

}
