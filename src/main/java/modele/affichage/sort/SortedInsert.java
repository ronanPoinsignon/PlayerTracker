package modele.affichage.sort;

import java.util.Comparator;
import java.util.List;

/**
 *
 * Permet de récupérer la valeur de l'indice correspondant à l'endroit d'insertion d'un élément dans une liste
 * par rapport à un {@link Comparator} donné.
 * Pour que la recherche fonctionne bien, la liste doit être triée avec le même {@link Comparator}.
 *
 * @author Ronan
 *
 * @param <T>
 */
public class SortedInsert<T> {

	private Comparator<T> comparator;

	public int getIndexInsertFromSort(final List<T> list, final T valueToInsert) {
		if(comparator == null) {
			return list.size();
		}

		for(var i = 0; i < list.size(); i++) {
			if(comparator.compare(list.get(i), valueToInsert) > 0) {
				return i;
			}
		}

		return list.size();
	}

	public void setComparator(final Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}
}