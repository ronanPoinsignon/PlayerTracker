package modele.event.mouse;

import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import modele.event.eventaction.DeleteEvent;
import modele.joueur.JoueurFx;

/**
 * Evénement de suppression d'une vidéo de la liste de vidéo.
 * @author ronan
 *
 */
public class MouseEventSuppression extends MouseEventHandler {

	public MouseEventSuppression(TableView<JoueurFx> table) {
		super(table);
	}

	@Override
	public void handle(MouseEvent event) {
		Thread th = new Thread(new DeleteEvent(table));
		th.setDaemon(true);
		th.start();
	}

}
