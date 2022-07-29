package modele.event.mouse;

import javafx.scene.input.MouseEvent;
import modele.affichage.ViewElement;
import modele.event.eventaction.AddEvent;
import modele.joueur.JoueurFx;
import modele.joueur.Serveur;
import service.ServerManager;
import service.ServiceManager;

/**
 * Evénement d'ajout d'un joueur à la liste de joueurs.
 * @author ronan
 *
 */
public class MouseEventAjout extends MouseEventHandler {

	ServerManager sm = ServiceManager.getInstance(ServerManager.class);

	private final String nom;
	private final String pseudo;
	private final Serveur serveur;

	public MouseEventAjout(final TableView<JoueurFx> table, final String nom, final String pseudo, final Serveur serveur) {
		super(table);
		this.nom = nom;
		this.pseudo = pseudo;
		this.serveur = serveur;
	}

	@Override
	public void handle(final MouseEvent event) {
		final var th = new Thread(new AddEvent(table, nom, pseudo, serveur));
		th.setDaemon(true);
		th.start();
	}

}