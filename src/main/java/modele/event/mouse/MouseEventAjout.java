package modele.event.mouse;

import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import modele.event.eventaction.AddEvent;
import modele.joueur.JoueurFx;

/**
 * Evénement d'ajout d'un joueur à la liste de joueurs.
 * @author ronan
 *
 */
public class MouseEventAjout extends MouseEventHandler {

	private final String nom;
	private final String pseudo;

	public MouseEventAjout(final TableView<JoueurFx> table, final String nom, final String pseudo) {
		super(table);
		this.nom = nom;
		this.pseudo = pseudo;
	}

	@Override
	public void handle(final MouseEvent event) {
		final var th = new Thread(new AddEvent(table, nom, pseudo));
		th.setDaemon(true);
		th.start();
	}

}