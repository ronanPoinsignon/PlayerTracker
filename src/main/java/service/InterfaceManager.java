package service;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class InterfaceManager implements IService {

	private BooleanProperty disableAjoutProperty = new SimpleBooleanProperty();
	private BooleanProperty disableModifierProperty = new SimpleBooleanProperty();
	private BooleanProperty disableTableProperty = new SimpleBooleanProperty();

	public BooleanProperty getDisableAjoutProperty() {
		return disableAjoutProperty;
	}

	public BooleanProperty getDisableModifierProperty() {
		return disableModifierProperty;
	}

	public BooleanProperty getDisableTableProperty() {
		return disableTableProperty;
	}


}
