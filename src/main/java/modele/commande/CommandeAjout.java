package modele.commande;

import java.util.List;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;
import service.SaveService;
import service.ServiceManager;
import service.TrayIconService;
import service.WebRequestScheduler;

/**
 * Classe permettant l'ajout de joueurs à la liste.
 * @author ronan
 *
 */
public class CommandeAjout extends CommandeListe<JoueurFx> {

	private final TrayIconService trayIconService = ServiceManager.getInstance(TrayIconService.class);
	private final WebRequestScheduler scheduler = ServiceManager.getInstance(WebRequestScheduler.class);
	private final SaveService saveService = ServiceManager.getInstance(SaveService.class);

	public CommandeAjout(final TableView<JoueurFx> table, final List<JoueurFx> listeJoueurs) {
		super(table, listeJoueurs);
	}

	public CommandeAjout(final TableView<JoueurFx> table, final JoueurFx joueur) {
		super(table, joueur);
	}

	@Override
	public boolean executer() {
		final var listeJoueursDejaPresentes = commandeUtil.addAll(table, elements);
		elements.removeAll(listeJoueursDejaPresentes);
		elements.forEach(trayIconService::bind);
		elements.forEach(scheduler::addJoueur);
		elements.forEach(saveService::addJoueur);
		scheduler.executeNow();
		return !elements.isEmpty(); //si cette liste est vide, aucun joueur n'a donc été ajouté et cette commande est donc inutile
	}

	@Override
	public boolean annuler() {
		commandeUtil.removeAll(table, elements);
		elements.forEach(trayIconService::unbind);
		elements.forEach(scheduler::removeJoueur);
		elements.forEach(saveService::removeJoueur);
		return !elements.isEmpty();
	}

	@Override
	public boolean reexecuter() {
		commandeUtil.addAll(table, elements);
		elements.forEach(trayIconService::bind);
		elements.forEach(saveService::addJoueur);
		return !elements.isEmpty();
	}
}