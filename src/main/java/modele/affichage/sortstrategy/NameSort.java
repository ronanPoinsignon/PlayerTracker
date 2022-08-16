package modele.affichage.sortstrategy;

import modele.joueur.Joueur;

public class NameSort<T extends Joueur> implements SortStrategy<T> {

	@Override
	public int compare(T o1, T o2) {
		final var online = Boolean.valueOf(o2.isInGame()).compareTo(o1.isInGame());
		
		if(online != 0)
			return online;
		
		final var value1 = o1.getNom().toLowerCase();
		final var value2 = o2.getNom().toLowerCase();
				
		return value1.compareTo(value2);
	}

}
