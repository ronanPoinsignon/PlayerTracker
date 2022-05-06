package modele.commande;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;

/**
 * Classe abstraite correspondant à toutes les commandes devant accéder à la table en elle-même.
 * @author ronan
 *
 */
public abstract class CommandeTable extends Commande {

	protected TableView<JoueurFx> table;

	protected CommandeTable(TableView<JoueurFx> table) {
		this.table = table;
	}
}
