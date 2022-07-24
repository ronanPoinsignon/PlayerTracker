package modele.commande;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		for(final JoueurFx joueur : elements) {
			listeIndex.add(table.getItems().indexOf(joueur));
			trayIconService.unbind(joueur);
			scheduler.removeJoueur(joueur);
			saveService.removeJoueur(joueur);
		}
		final var listeJoueursNonPresents = commandeUtil.removeAll(table, elements);
		elements.removeAll(listeJoueursNonPresents);
		return !elements.isEmpty();
	}

	@Override
	public boolean annuler() {
		for(var i = 0; i < elements.size(); i++) {
			final int index = listeIndex.get(i);
			try {
				final var joueur = elements.get(i);
				commandeUtil.add(table, joueur, index);
				trayIconService.bind(joueur);
				scheduler.executeNow(joueur);
				scheduler.addJoueur(joueur);
				saveService.addJoueur(joueur);
			} catch (UnsupportedOperationException | ClassCastException
					| IllegalArgumentException | JoueurDejaPresentException e) {
				alerteService.alert(e);
			}
		}
		listeIndex = new ArrayList<>();
		return !elements.isEmpty();
	}

}