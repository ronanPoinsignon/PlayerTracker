package modele.affichage.sortstrategy;

import java.util.Comparator;

import javafx.beans.property.StringProperty;

public interface SortStrategy<T> extends Comparator<T> {

	Comparator<T> getComparator();

	@Override
	default int compare(final T o1, final T o2) {
		return getComparator().compare(o1, o2);
	}
	
	StringProperty getLabelProperty();
}