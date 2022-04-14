package modele.commande;

import java.util.Collections;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;

/**
 * Commande permettant l'inversion de deux lignes dans la table
 * @author ronan
 *
 */
public class CommandeInversion extends Commande {

	int inv1;
	int inv2;

	public CommandeInversion(TableView<JoueurFx> table, int inv1, int inv2) {
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
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	@Override
	public boolean annuler() {
		try {
			Collections.swap(table.getItems(), inv2, inv1);
			return true;
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
