package modele.commande;

import java.util.List;
import java.util.stream.Collectors;

import modele.affichage.ViewElement;
import modele.joueur.JoueurFx;
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

	public CommandeAjout(final ViewElement<JoueurFx> table, final List<JoueurFx> listeJoueurs) {
		super(table, listeJoueurs);
	}

	public CommandeAjout(final ViewElement<JoueurFx> table, final JoueurFx joueur) {
		super(table, joueur);
	}

	@Override
	public boolean executer() {
		final var listeJoueursDejaPresentes = commandeUtil.addAll(table, elements);
		elements.removeAll(listeJoueursDejaPresentes);
		elements.stream().forEach(joueurFx -> {
			trayIconService.bind(joueurFx);
			scheduler.addJoueur(joueurFx);
		});
		saveService.addJoueurs(elements.stream().map(JoueurFx::getJoueur).collect(Collectors.toList()));
		scheduler.executeNow(elements);
		return !elements.isEmpty(); //si cette liste est vide, aucun joueur n'a donc été ajouté et cette commande est donc inutile
	}

	@Override
	public boolean annuler() {
		commandeUtil.removeAll(table, elements);
		elements.forEach(joueurFx -> {
			trayIconService.unbind(joueurFx);
			scheduler.removeJoueur(joueurFx);
		});
		saveService.removeJoueurs(elements.stream().map(JoueurFx::getJoueur).collect(Collectors.toList()));
		return !elements.isEmpty();
	}

	@Override
	public boolean reexecuter() {
		commandeUtil.addAll(table, elements);
		elements.forEach(joueurFx -> {
			trayIconService.bind(joueurFx);
			scheduler.addJoueur(joueurFx);
		});
		saveService.addJoueurs(elements.stream().map(JoueurFx::getJoueur).collect(Collectors.toList()));
		return !elements.isEmpty();
	}
}