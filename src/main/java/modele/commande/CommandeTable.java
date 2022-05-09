package modele.commande;

import modele.affichage.ViewElement;

/**
 * Classe abstraite correspondant à toutes les commandes devant accéder à la table en elle-même.
 * @author ronan
 *
 */
public abstract class CommandeTable<T> extends Commande<T> {

	protected ViewElement<T> table;

	protected CommandeTable(ViewElement<T> table) {
		this.table = table;
	}
}
