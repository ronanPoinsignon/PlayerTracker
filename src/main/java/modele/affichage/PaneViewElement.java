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
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
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
	private final List<Pane> sortedPane = new ArrayList<>();
	
	protected static final int WIDTH = 300;
	protected static final int HEIGHT = 200;
	protected static final int WIDTH_PADDING = 40;
	protected static final int HEIGHT_PADDING = 30;
	protected static final int ELEMENTS_PER_ROW = 3;

	public PaneViewElement() {
		elements.addListener(this::setOnChangeEvent);
		getChildren().addListener((ListChangeListener<? super Node>) change -> {
			change.next();
			
			if(change.wasAdded()) {
				sortedPane.addAll(change.getAddedSubList().stream().map(element -> (Pane) element).toList());
			}
			else if(change.wasRemoved()) {
				sortedPane.removeAll(change.getRemoved().stream().map(element -> (Pane) element).toList());
			}
			
			System.out.println(sortedPane.size());
		});
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

			paneMap.put(paneElement, element);
			insertValueBasedOnSort(paneElement);

			paneElement.setOnMouseClicked(evt -> {
				elementProperty.set(element);
				index = getChildren().indexOf(paneElement);
			});
		}
	}

	private void insertValueBasedOnSort(final Pane paneElement) {
		final var index = sort.getIndexInsertFromSort(sortedPane.stream().map(pane -> (Node) pane).toList(), paneElement);

		final var oldChild = (Pane) setChild(paneElement, index);
		
		sortedPane.sort(sort.getComparator());
		
		if(oldChild == null || oldChild == paneElement)
			return;
		
		insertValueBasedOnSort(oldChild);
	}
	
	public Node setChild(final Pane paneElement, int index) {	
		final var column = index % ELEMENTS_PER_ROW;
		final var row = index / ELEMENTS_PER_ROW;
		
		final var old = getChildren().stream()
				.filter(child -> getColumnIndex(child) == column && getRowIndex(child) == row)
				.findFirst();
		
		if(old.isPresent()) {
			getChildren().remove(old.get());
		}
				
		add(paneElement, column, row);
		
		if(getRowConstraints().size() == row) {
			getRowConstraints().add(new RowConstraints(0, HEIGHT + HEIGHT_PADDING, HEIGHT + HEIGHT_PADDING, null, VPos.CENTER, false));
		}
		
		if(getColumnConstraints().size() == column) {
			getColumnConstraints().add(new ColumnConstraints(0, WIDTH + WIDTH_PADDING, WIDTH + WIDTH_PADDING, null, HPos.LEFT, false));
		}
		
		if(old.isEmpty())
			return null;
				
		return old.get();
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