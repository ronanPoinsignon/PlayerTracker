package modele.commande;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modele.affichage.ViewElement;
import modele.joueur.JoueurFx;
import service.AlertFxService;
import service.ServiceManager;
import service.TrayIconService;
import service.WebRequestScheduler;

/**
 * Classe permettant la suppression de vidéos de la liste.
 * @author ronan
 *
 */
public class CommandeSuppression extends CommandeListe<JoueurFx> {

	private TrayIconService trayIconService = ServiceManager.getInstance(TrayIconService.class);
	WebRequestScheduler scheduler = ServiceManager.getInstance(WebRequestScheduler.class);
	private AlertFxService alerteService = ServiceManager.getInstance(AlertFxService.class);

	private List<Integer> listeIndex = new ArrayList<>();

	public CommandeSuppression(ViewElement<JoueurFx> table, List<JoueurFx> elements) {
		super(table, elements);
	}

	public CommandeSuppression(ViewElement<JoueurFx> table,JoueurFx element) {
		super(table, Arrays.asList(element));
	}

	@Override
	public boolean executer() {
		for(JoueurFx joueur : elements) {
			listeIndex.add(table.getItems().indexOf(joueur));
			trayIconService.unbind(joueur);
			scheduler.removeJoueur(joueur);
			saveService.removeJoueur(joueur);
		}
		List<JoueurFx> listeVideosNonPresentes = commandeUtil.removeAll(table, elements);
		elements.removeAll(listeVideosNonPresentes);
		return !elements.isEmpty();
	}

	@Override
	public boolean annuler() {
		for(var i = 0; i < elements.size(); i++) {
			int index = listeIndex.get(i);
			try {
				JoueurFx joueur = elements.get(i);
				commandeUtil.add(table, joueur, index);
				trayIconService.bind(joueur);
				scheduler.addJoueur(joueur);
				saveService.addJoueur(joueur);
			} catch (UnsupportedOperationException | ClassCastException
					| IllegalArgumentException | JoueurDejaPresentException e) {
				alerteService.alert(e);
			}
		}
		scheduler.executeNow();
		listeIndex = new ArrayList<>();
		return !elements.isEmpty();
	}

}
