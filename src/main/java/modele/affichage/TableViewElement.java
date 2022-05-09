package modele.affichage;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.TableView;

public class TableViewElement<T> extends TableView<T> implements ViewElement<T> {

	public TableViewElement() {
	}

	@Override
	public ReadOnlyObjectProperty<T> selectedItemProperty() {
		return getSelectionModel().selectedItemProperty();
	}

	@Override
	public int getSelectedIndex() {
		return getSelectionModel().getSelectedIndex();
	}

}
