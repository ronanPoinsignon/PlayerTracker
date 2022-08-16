package modele.affichage.sortstrategy;

import java.util.Comparator;

import modele.joueur.Joueur;

public class ServerSort<T extends Joueur> implements SortStrategy<T> {

	@Override
	public Comparator<T> getComparator() {
		return Comparator.comparing(T::isInGame).thenComparing((j1, j2) -> {
			final var value1 = j1.getServer().getLabel().toLowerCase();
			final var value2 = j2.getServer().getLabel().toLowerCase();

			return value1.compareTo(value2);
		});
	}

}
