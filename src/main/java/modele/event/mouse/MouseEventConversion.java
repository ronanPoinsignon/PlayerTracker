package modele.event.mouse;

import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import modele.joueur.JoueurFx;

/**
 * Evénement de conversion de la liste de vidéos.
 * @author ronan
 *
 */
public class MouseEventConversion extends MouseEventHandler {

	public MouseEventConversion(TableView<JoueurFx> table) {
		super(table);
	}

	@Override
	public void handle(MouseEvent event) {
		//		selection.convertirListe();
	}

}
