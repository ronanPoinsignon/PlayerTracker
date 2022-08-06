package modele.commande;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modele.affichage.ViewElement;
import modele.exception.JoueurDejaPresentException;
import modele.joueur.JoueurFx;
import service.AlertFxService;
import service.ServiceManager;
import service.TrayIconService;
import service.WebRequestScheduler;

/**
 * Classe permettant la suppression de joueurs de la liste.
 * @author ronan
 *
 */
public class CommandeSuppression extends CommandeListe<JoueurFx> {

	private final TrayIconService trayIconService = ServiceManager.getInstance(TrayIconService.class);
	WebRequestScheduler scheduler = ServiceManager.getInstance(WebRequestScheduler.class);
	private final AlertFxService alerteService = ServiceManager.getInstance(AlertFxService.class);

	private List<Integer> listeIndex = new ArrayList<>();

	public CommandeSuppression(ViewElement<JoueurFx> table, List<JoueurFx> elements) {
		super(table, elements);
	}

	public CommandeSuppression(ViewElement<JoueurFx> table,JoueurFx element) {
		super(table, Arrays.asList(element));
	}

	@Override
	public boolean executer() {
		elements.forEach(joueurFx -> {
			listeIndex.add(table.getItems().indexOf(joueurFx));
			trayIconService.unbind(joueurFx);
			scheduler.removeJoueur(joueurFx);
			saveService.removeJoueur(joueurFx.getJoueur());
		});
		final var listeJoueursNonPresents = commandeUtil.removeAll(table, elements);
		elements.removeAll(listeJoueursNonPresents);
		return !elements.isEmpty();
	}

	@Override
	public boolean annuler() {
		for(var i = 0; i < elements.size(); i++) {
			final int index = listeIndex.get(i);
			try {
				final var joueurFx = elements.get(i);
				commandeUtil.add(table, joueurFx, index);
				trayIconService.bind(joueurFx);
				scheduler.executeNow(joueurFx);
				scheduler.addJoueur(joueurFx);
				saveService.addJoueur(joueurFx.getJoueur());
			} catch (UnsupportedOperationException | ClassCastException
					| IllegalArgumentException | JoueurDejaPresentException e) {
				alerteService.alert(e);
			}
		}
		listeIndex = new ArrayList<>();
		return !elements.isEmpty();
	}

}