package modele.affichage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ElementController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import modele.affichage.sort.SortedInsert;
import service.FileManager;
import service.ServiceManager;

public abstract class PaneViewElement<T> extends GridPane implements ViewElement<T> {

	protected final FileManager fm = ServiceManager.getInstance(FileManager.class);

	private int index;
	private final Map<Pane, T> paneMap = new HashMap<>();
	private final SortedInsert<Node> sort = new SortedInsert<>();
	private final ObjectProperty<T> elementProperty = new SimpleObjectProperty<>();
	private final ObservableList<T> elements = FXCollections.observableArrayList(new ArrayList<>());

	public PaneViewElement() {
		elements.addListener(this::setOnChangeEvent);
	}

	public abstract FXMLLoader createLoader() throws MalformedURLException;


	private void setOnChangeEvent(final Change<? extends T> change) {
		change.next();

		if(!change.wasAdded()) {
			return;
		}

		for(final T element : change.getAddedSubList()) {

			final Pane paneElement;
			final FXMLLoader loader;
			final ElementController<T> controller;

			try {
				loader = createLoader();
				paneElement = loader.load();
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}

			controller = loader.getController();
			controller.setElement(element);

			final var position = calculateNextPosition();

			paneMap.put(paneElement, element);
			insertValueBasedOnSort(paneElement);

			paneElement.setTranslateX(position[0]);
			paneElement.setTranslateY(position[1]);

			setPrefHeight(position[1] + 240);

			paneElement.setOnMouseClicked(evt -> {
				elementProperty.set(element);
				index = getChildren().indexOf(paneElement);
			});
		}
	}

	private void insertValueBasedOnSort(final Pane paneElement) {
		final var index = sort.getIndexInsertFromSort(getChildren(), paneElement);
		getChildren().add(index, paneElement);
	}

	public int[] calculateNextPosition() {
		final var size = elements.size() - 1;

		final var rowPosition = size % 3;
		final var rowNumber = size / 3;

		final var x = rowPosition * (300 + 40);
		final var y= rowNumber * 200;

		return new int[] {x, y};
	}

	@Override
	public ReadOnlyObjectProperty<T> selectedItemProperty() {
		return elementProperty;
	}

	@Override
	public int getSelectedIndex() {
		return index;
	}

	@Override
	public List<T> getItems() {
		return elements;
	}

	public void setSort(final Comparator<Node> sort) {
		this.sort.setComparator(sort);
		updateSort();
	}

	public void updateSort() {
		if(sort == null) {
			return;
		}
		getChildren().clear();
		paneMap.keySet().stream().forEach(this::insertValueBasedOnSort);
	}

	public Map<Pane, T> getPaneMap() {
		return paneMap;
	}

}