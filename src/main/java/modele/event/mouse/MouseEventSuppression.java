package modele.event.mouse;

import javafx.scene.input.MouseEvent;
import modele.affichage.ViewElement;
import modele.event.eventaction.DeleteEvent;
import modele.joueur.JoueurFx;

/**
 * Evénement de suppression d'une vidéo de la liste de vidéo.
 * @author ronan
 *
 */
public class MouseEventSuppression extends MouseEventHandler {

	public MouseEventSuppression(final ViewElement<JoueurFx> table) {
		super(table);
	}

	@Override
	public void handle(final MouseEvent event) {
		final var th = new Thread(new DeleteEvent(table));
		th.setDaemon(true);
		th.start();
	}

}