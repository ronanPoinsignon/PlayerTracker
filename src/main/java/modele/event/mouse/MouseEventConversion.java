package modele.event.mouse;

import javafx.scene.input.MouseEvent;
import modele.affichage.ViewElement;
import modele.joueur.JoueurFx;

/**
 * Evénement de conversion de la liste de vidéos.
 * @author ronan
 *
 */
public class MouseEventConversion extends MouseEventHandler {

	public MouseEventConversion(ViewElement<JoueurFx> table) {
		super(table);
	}

	@Override
	public void handle(MouseEvent event) {
		//		selection.convertirListe();
	}

}
