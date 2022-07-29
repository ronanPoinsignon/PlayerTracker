package modele.commande;

import java.util.Collections;

import modele.affichage.ViewElement;

/**
 * Commande permettant l'inversion de deux lignes dans la table
 * @author ronan
 *
 */
public class CommandeInversion<T> extends CommandeTable<T> {

	int inv1;
	int inv2;

	public CommandeInversion(ViewElement<T> table, int inv1, int inv2) {
		super(table);
		this.inv1 = inv1;
		this.inv2 = inv2;
	}

	@Override
	public boolean executer() {
		try {
			Collections.swap(table.getItems(), inv1, inv2);
			return true;
		}
		catch(final ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	@Override
	public boolean annuler() {
		try {
			Collections.swap(table.getItems(), inv2, inv1);
			return true;
		}
		catch(final ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}