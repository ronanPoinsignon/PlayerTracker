package modele.commande;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;
import service.ServiceManager;
import service.TrayIconService;
import service.WebRequestScheduler;

/**
 * Commande permettant de changer la table existante en une nouvelle.
 * @author ronan
 *
 */
public class CommandeReset extends CommandeListe {

	private TrayIconService trayIconService = ServiceManager.getInstance(TrayIconService.class);
	private WebRequestScheduler scheduler = ServiceManager.getInstance(WebRequestScheduler.class);

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
		listeJoueursSupprimes.forEach(trayIconService::unbind);
		listeJoueursSupprimes.forEach(scheduler::removeJoueur);
		return !listeJoueurs.isEmpty();
	}

	@Override
	public boolean annuler() {
		commandeUtil.removeAll(table);
		commandeUtil.addAll(table, listeJoueursSupprimes);
		listeJoueursSupprimes.forEach(trayIconService::bind);
		listeJoueursSupprimes.forEach(scheduler::addJoueur);
		return !listeJoueurs.isEmpty();
	}

}
