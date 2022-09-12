package modele.affichage.sortstrategy;

import java.util.Comparator;

import javafx.beans.property.StringProperty;
import modele.joueur.Joueur;
import service.DictionnaireService;
import service.ServiceManager;

public class NameSort<T extends Joueur> implements SortStrategy<T> {

	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);

	@Override
	public Comparator<T> getComparator() {
		return Comparator.comparing(T::isInGame).reversed().thenComparing((j1, j2) -> {
			var value1 = j1.getNom().toLowerCase();
			var value2 = j2.getNom().toLowerCase();

			if(value1 == null || value1.isEmpty()) {
				value1 = j1.getPseudo().toLowerCase();
			}
			if(value2 == null || value2.isEmpty()) {
				value2 = j2.getPseudo().toLowerCase();
			}

			return value1.compareTo(value2);
		});
	}

	@Override
	public StringProperty getLabelProperty() {
		return dictionnaire.getText("sortName");
	}

}
