package modele.affichage;

import java.util.List;

import javafx.beans.property.ReadOnlyBooleanProperty;

public interface ViewElement<T> {

	ReadOnlyBooleanProperty disableProperty();
	int getSelectedIndex();
	List<T> getItems();
}
