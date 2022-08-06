package modele.commande;

import java.util.List;

import modele.affichage.ViewElement;
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

	public CommandeAjout(ViewElement<JoueurFx> table, List<JoueurFx> listeJoueurs) {
		super(table, listeJoueurs);
	}

	public CommandeAjout(ViewElement<JoueurFx> table, JoueurFx joueur) {
		super(table, joueur);
	}

	@Override
	public boolean executer() {
		final var listeJoueursDejaPresentes = commandeUtil.addAll(table, elements);
		elements.removeAll(listeJoueursDejaPresentes);
		elements.stream().forEach(joueurFx -> {
			trayIconService.bind(joueurFx);
			scheduler.executeNow(joueurFx);
			scheduler.addJoueur(joueurFx);
			saveService.addJoueur(joueurFx.getJoueur());
		});
		return !elements.isEmpty(); //si cette liste est vide, aucun joueur n'a donc été ajouté et cette commande est donc inutile
	}

	@Override
	public boolean annuler() {
		commandeUtil.removeAll(table, elements);
		elements.forEach(joueurFx -> {
			trayIconService.unbind(joueurFx);
			scheduler.removeJoueur(joueurFx);
			saveService.removeJoueur(joueurFx.getJoueur());
		});
		return !elements.isEmpty();
	}

	@Override
	public boolean reexecuter() {
		commandeUtil.addAll(table, elements);
		elements.forEach(joueurFx -> {
			trayIconService.bind(joueurFx);
			scheduler.addJoueur(joueurFx);
			saveService.addJoueur(joueurFx.getJoueur());
		});
		return !elements.isEmpty();
	}
}