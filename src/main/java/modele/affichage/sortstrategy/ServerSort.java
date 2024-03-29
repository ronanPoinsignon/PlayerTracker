package modele.affichage.sortstrategy;

import java.util.Comparator;

import javafx.beans.property.StringProperty;
import modele.joueur.Joueur;
import service.DictionnaireService;
import service.ServiceManager;

public class ServerSort<T extends Joueur> implements SortStrategy<T> {

	private final DictionnaireService dictionnaire = ServiceManager.getInstance(DictionnaireService.class);

	@Override
	public Comparator<T> getComparator() {
		return Comparator.comparing(T::isInGame).reversed().thenComparing((j1, j2) -> {
			final var value1 = j1.getServer().getLabel().toLowerCase();
			final var value2 = j2.getServer().getLabel().toLowerCase();

			return value1.compareTo(value2);
		});
	}

	@Override
	public StringProperty getLabelProperty() {
		return dictionnaire.getText("sortServer");
	}

}
