package modele.commande;

import java.util.List;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;
import service.TrayIconService;
import service.ServiceManager;

/**
 * Classe permettant l'ajout de vidéos à la liste.
 * @author ronan
 *
 */
public class CommandeAjout extends CommandeListe {

	private TrayIconService trayIconService = ServiceManager.getInstance(TrayIconService.class);

	public CommandeAjout(TableView<JoueurFx> table, List<JoueurFx> listeJoueurs) {
		super(table, listeJoueurs);
	}

	public CommandeAjout(TableView<JoueurFx> table, JoueurFx joueur) {
		super(table, joueur);
	}

	@Override
	public boolean executer() {
		List<JoueurFx> listeJoueursDejaPresentes = commandeUtil.addAll(table, listeJoueurs);
		listeJoueurs.removeAll(listeJoueursDejaPresentes);
		listeJoueurs.forEach(trayIconService::bind);
		return !listeJoueurs.isEmpty(); //si cette liste est vide, aucun joueur n'a donc été ajouté et cette commande est donc inutile
	}

	@Override
	public boolean annuler() {
		commandeUtil.removeAll(table, listeJoueurs);
		listeJoueurs.forEach(trayIconService::unbind);
		return !listeJoueurs.isEmpty();
	}

	@Override
	public boolean reexecuter() {
		commandeUtil.addAll(table, listeJoueurs);
		listeJoueurs.forEach(trayIconService::bind);
		return !listeJoueurs.isEmpty();
	}
}
