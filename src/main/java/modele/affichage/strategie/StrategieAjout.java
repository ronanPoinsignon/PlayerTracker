package modele.affichage.strategie;

import java.io.IOException;

import controller.ElementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import modele.affichage.PaneViewElement;

public class StrategieAjout<T> implements IStrategiePaneViewElement<T> {

	private final PaneViewElement<T> viewElement;

	public StrategieAjout(final PaneViewElement<T> viewElement) {
		this.viewElement = viewElement;
	}

	@Override
	public void execute(final T element) {
		final Pane paneElement;
		final FXMLLoader loader;
		final ElementController<T> controller;

		try {
			loader = viewElement.createLoader();
			paneElement = loader.load();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		controller = loader.getController();
		controller.setElement(element);

		viewElement.getPaneMap().put(paneElement, element);
		viewElement.insertValueBasedOnSort(paneElement);

		paneElement.setOnMouseClicked(evt -> viewElement.selectItem(paneElement));

	}

}
