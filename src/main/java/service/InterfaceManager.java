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

	private BooleanProperty visibleAjoutProperty = new SimpleBooleanProperty();
	private BooleanProperty visibleModifierProperty = new SimpleBooleanProperty();

	private BooleanProperty disablePseudoProperty = new SimpleBooleanProperty();
	private BooleanProperty disableNomProperty = new SimpleBooleanProperty();

	public InterfaceManager() {
		debloquerInterface();
	}

	public BooleanProperty getDisableAjoutProperty() {
		return disableAjoutProperty;
	}

	public BooleanProperty getDisableModifierProperty() {
		return disableModifierProperty;
	}

	public BooleanProperty getDisableTableProperty() {
		return disableTableProperty;
	}

	public BooleanProperty getDisablePseudoProperty() {
		return disablePseudoProperty;
	}

	public BooleanProperty getDisableNomProperty() {
		return disableNomProperty;
	}

	public BooleanProperty getVisibleAjoutProperty() {
		return visibleAjoutProperty;
	}

	public BooleanProperty getVisibleModifierProperty() {
		return visibleModifierProperty;
	}

	public void bloquerInterface() {
		setAllDisable(true);
	}

	public void debloquerInterface() {
		setAllDisable(false);
		visibleAjoutProperty.set(true);
		visibleModifierProperty.set(false);
	}

	private void setAllDisable(boolean value) {
		disableAjoutProperty.set(value);
		disableModifierProperty.set(value);
		disableTableProperty.set(value);
		disablePseudoProperty.set(value);
		disableNomProperty.set(value);
	}

	public void setDisableAjoutProperty(boolean value) {
		disableAjoutProperty.set(value);
	}

	public void setDisableModifierProperty(boolean value) {
		disableModifierProperty.set(value);
	}

	public void setDisableTableProperty(boolean value) {
		disableTableProperty.set(value);
	}

	public void setDisablePseudoProperty(boolean value) {
		disablePseudoProperty.set(value);
	}

	public void setDisableNomProperty(boolean value) {
		disableNomProperty.set(value);
	}

	public void setVisibleAjoutProperty() {
		visibleAjoutProperty.set(true);
		visibleModifierProperty.set(false);
	}

	public void setVisibleModifierProperty() {
		visibleAjoutProperty.set(false);
		visibleModifierProperty.set(true);
	}

	public void reset() {
		observateurs.forEach(obs -> {
			obs.notifyNewStringValueNom("");
			obs.notifyNewStringValuePseudo("");
		});
		debloquerInterface();
	}

	public void setNomValue(String value) {
		observateurs.forEach(obs -> obs.notifyNewStringValueNom(value));
	}

	public void setPseudoValue(String value) {
		observateurs.forEach(obs ->	obs.notifyNewStringValuePseudo(value));
	}

	public void addObs(ObservateurInterface obs) {
		observateurs.add(obs);
	}

	public void removeObs(ObservateurInterface obs) {
		observateurs.remove(obs);
	}
}
