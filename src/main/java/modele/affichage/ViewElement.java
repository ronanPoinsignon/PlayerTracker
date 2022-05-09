package modele.affichage;

import java.util.List;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;

public interface ViewElement<T> {

	ReadOnlyBooleanProperty disableProperty();
	ReadOnlyObjectProperty<T> selectedItemProperty();
	int getSelectedIndex();
	List<T> getItems();
}
