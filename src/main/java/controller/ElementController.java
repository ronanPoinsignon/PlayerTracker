package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;

public abstract class ElementController<T> implements Initializable {

	protected final SimpleObjectProperty<T> element = new SimpleObjectProperty<>();

	public abstract ChangeListener<T> getOnChangeListenerEvent();

	public void setElement(final T element) {
		this.element.addListener(getOnChangeListenerEvent());
		this.element.set(element);
	}

}
