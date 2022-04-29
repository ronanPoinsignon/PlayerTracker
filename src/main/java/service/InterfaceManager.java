package service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import modele.observer.ObservateurInterface;

public class InterfaceManager implements IService {

	private List<ObservateurInterface> observateurs = new ArrayList<>();

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

	public void bloquerInterface() {
		disableAjoutProperty.set(true);
		disableTableProperty.set(true);
	}

	public void debloquerInterface() {
		disableAjoutProperty.set(false);
		disableTableProperty.set(false);
	}

	public void reset() {
		observateurs.forEach(obs -> obs.notifyNewStringValue(""));
	}

	public void addObs(ObservateurInterface obs) {
		observateurs.add(obs);
	}

	public void removeObs(ObservateurInterface obs) {
		observateurs.remove(obs);
	}
}
