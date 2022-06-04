package modele.commande;

import javafx.scene.control.TableView;

/**
 * Classe abstraite correspondant à toutes les commandes devant accéder à la table en elle-même.
 * @author ronan
 *
 */
public abstract class CommandeTable<T> extends Commande<T> {

	protected TableView<T> table;

	protected CommandeTable(final TableView<T> table) {
		this.table = table;
	}
}