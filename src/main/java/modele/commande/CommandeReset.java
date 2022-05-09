package modele.commande;

import java.util.ArrayList;
import java.util.List;

import modele.affichage.ViewElement;
import modele.joueur.JoueurFx;
import service.ServiceManager;
import service.TrayIconService;
import service.WebRequestScheduler;

/**
 * Commande permettant de changer la table existante en une nouvelle.
 * @author ronan
 *
 */
public class CommandeReset extends CommandeListe<JoueurFx> {

	private TrayIconService trayIconService = ServiceManager.getInstance(TrayIconService.class);
	private WebRequestScheduler scheduler = ServiceManager.getInstance(WebRequestScheduler.class);

	List<JoueurFx> listeJoueursSupprimes = new ArrayList<>();

	public CommandeReset(ViewElement<JoueurFx> table, List<JoueurFx> elements) {
		super(table, elements);
	}

	@Override
	public boolean executer() {
		if(table.getItems().equals(elements)) {
			return false;
		}
		listeJoueursSupprimes = commandeUtil.removeAll(table);
		commandeUtil.addAll(table, elements);
		listeJoueursSupprimes.forEach(trayIconService::unbind);
		listeJoueursSupprimes.forEach(scheduler::removeJoueur);
		listeJoueursSupprimes.forEach(saveService::removeJoueur);
		return !elements.isEmpty();
	}

	@Override
	public boolean annuler() {
		commandeUtil.removeAll(table);
		commandeUtil.addAll(table, listeJoueursSupprimes);
		listeJoueursSupprimes.forEach(trayIconService::bind);
		listeJoueursSupprimes.forEach(scheduler::addJoueur);
		listeJoueursSupprimes.forEach(saveService::addJoueur);
		return !elements.isEmpty();
	}

}
