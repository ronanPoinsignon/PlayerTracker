package modele.commande;

import java.util.List;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;
import service.NotificationService;
import service.ServiceManager;

/**
 * Classe permettant l'ajout de vidéos à la liste.
 * @author ronan
 *
 */
public class CommandeAjout extends CommandeListe {

	private NotificationService notificationService = ServiceManager.getInstance(NotificationService.class);

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
		listeJoueurs.forEach(notificationService::bind);
		return !listeJoueurs.isEmpty(); //si cette liste est vide, aucun joueur n'a donc été ajouté et cette commande est donc inutile
	}

	@Override
	public boolean annuler() {
		commandeUtil.removeAll(table, listeJoueurs);
		listeJoueurs.forEach(notificationService::unbind);
		return !listeJoueurs.isEmpty();
	}

	@Override
	public boolean reexecuter() {
		commandeUtil.addAll(table, listeJoueurs);
		listeJoueurs.forEach(notificationService::bind);
		return !listeJoueurs.isEmpty();
	}
}
