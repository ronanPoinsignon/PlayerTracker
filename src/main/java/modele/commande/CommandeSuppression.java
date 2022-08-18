package modele.commande;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.TableView;
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

	public CommandeSuppression(final TableView<JoueurFx> table, final List<JoueurFx> elements) {
		super(table, elements);
	}

	public CommandeSuppression(final TableView<JoueurFx> table,final JoueurFx element) {
		super(table, Arrays.asList(element));
	}

	@Override
	public boolean executer() {
		elements.forEach(joueurFx -> {
			listeIndex.add(table.getItems().indexOf(joueurFx));
			trayIconService.unbind(joueurFx);
			scheduler.removeJoueur(joueurFx);
		});
		saveService.removeJoueurs(elements.stream().map(JoueurFx::getJoueur).collect(Collectors.toList()));
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
			} catch (UnsupportedOperationException | ClassCastException
					| IllegalArgumentException | JoueurDejaPresentException e) {
				alerteService.alert(e);
			}
			saveService.addJoueurs(elements.stream().map(JoueurFx::getJoueur).collect(Collectors.toList()));
		}
		listeIndex = new ArrayList<>();
		return !elements.isEmpty();
	}

}