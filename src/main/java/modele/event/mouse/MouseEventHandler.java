package modele.event.mouse;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import modele.affichage.ViewElement;
import modele.joueur.JoueurFx;

/**
 * Classe permettant la gestion des événement de la souris lors d'un clic sur un item.
 * @author ronan
 *
 */
public abstract class MouseEventHandler implements EventHandler<MouseEvent> {

	protected ViewElement<JoueurFx> table;

	protected MouseEventHandler(final ViewElement<JoueurFx> table) {
		this.table = table;
	}
}