package modele.commande;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;
import service.NotificationService;
import service.ServiceManager;

/**
 * Commande permettant de changer la table existante en une nouvelle.
 * @author ronan
 *
 */
public class CommandeReset extends CommandeListe {

	private NotificationService notificationService = ServiceManager.getInstance(NotificationService.class);
	List<JoueurFx> listeJoueursSupprimes = new ArrayList<>();

	public CommandeReset(TableView<JoueurFx> table, List<JoueurFx> listeVideos) {
		super(table, listeVideos);
	}

	@Override
	public boolean executer() {
		if(table.getItems().equals(listeJoueurs)) {
			return false;
		}
		listeJoueursSupprimes = commandeUtil.removeAll(table);
		commandeUtil.addAll(table, listeJoueurs);
		listeJoueursSupprimes.forEach(notificationService::unbind);
		return !listeJoueurs.isEmpty();
	}

	@Override
	public boolean annuler() {
		commandeUtil.removeAll(table);
		commandeUtil.addAll(table, listeJoueursSupprimes);
		listeJoueursSupprimes.forEach(notificationService::bind);
		return !listeJoueurs.isEmpty();
	}

}
