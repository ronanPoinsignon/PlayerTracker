package modele.commande;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;
import service.NotificationService;
import service.ServiceManager;

/**
 * Classe permettant la suppression de vidéos de la liste.
 * @author ronan
 *
 */
public class CommandeSuppression extends CommandeListe {

	private NotificationService notificationService = ServiceManager.getInstance(NotificationService.class);
	private List<Integer> listeIndex = new ArrayList<>();

	public CommandeSuppression(TableView<JoueurFx> table, List<JoueurFx> listeJoueurs) {
		super(table, listeJoueurs);
	}

	public CommandeSuppression(TableView<JoueurFx> table,JoueurFx joueur) {
		super(table, Arrays.asList(joueur));
	}

	@Override
	public boolean executer() {
		for(JoueurFx joueur : listeJoueurs) {
			listeIndex.add(table.getItems().indexOf(joueur));
			notificationService.unbind(joueur);
		}
		List<JoueurFx> listeVideosNonPresentes = commandeUtil.removeAll(table, listeJoueurs);
		listeJoueurs.removeAll(listeVideosNonPresentes);
		return !listeJoueurs.isEmpty();
	}

	@Override
	public boolean annuler() {
		for(var i = 0; i < listeJoueurs.size(); i++) {
			int index = listeIndex.get(i);
			try {
				JoueurFx joueur = listeJoueurs.get(i);
				commandeUtil.add(table, joueur, index);
				notificationService.bind(joueur);
			} catch (UnsupportedOperationException | ClassCastException | NullPointerException
					| IllegalArgumentException | JoueurDejaPresentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		listeIndex = new ArrayList<>();
		return !listeJoueurs.isEmpty();
	}

}
