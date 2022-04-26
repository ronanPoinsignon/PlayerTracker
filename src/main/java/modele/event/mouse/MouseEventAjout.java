package modele.event.mouse;

import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import modele.event.eventaction.AddEvent;
import modele.joueur.JoueurFx;

/**
 * Evénement d'ajout d'une vidéo à la liste de vidéos.
 * @author ronan
 *
 */
public class MouseEventAjout extends MouseEventHandler {

	private String nom;
	private String pseudo;

	public MouseEventAjout(TableView<JoueurFx> table, String nom, String pseudo) {
		super(table);
		this.nom = nom;
		this.pseudo = pseudo;
	}

	@Override
	public void handle(MouseEvent event) {
		var th = new Thread(new AddEvent(table, nom, pseudo));
		th.setDaemon(true);
		th.start();
	}

}
