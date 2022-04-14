package modele.commande;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;

/**
 * Classe abstraite permettant d'effectuer des actions sur la liste de vidéos.
 * @author ronan
 *
 */
public abstract class Commande implements CommandeInterface {

	CommandeUtil commandeUtil = new CommandeUtil();

	protected TableView<JoueurFx> table;

	protected Commande(TableView<JoueurFx> table) {
		this.table = table;
	}

}
