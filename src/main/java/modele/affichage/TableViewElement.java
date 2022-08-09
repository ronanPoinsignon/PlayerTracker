package modele.affichage;

import javafx.scene.control.TableView;

public class TableViewElement<T> extends TableView<T> implements ViewElement<T> {

	public TableViewElement() {
	}

	@Override
	public int getSelectedIndex() {
		return getSelectionModel().getSelectedIndex();
	}

}
