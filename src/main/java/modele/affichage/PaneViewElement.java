package modele.affichage;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import modele.affichage.strategie.IStrategiePaneViewElement;
import modele.affichage.strategie.StrategieAjout;
import modele.affichage.strategie.StrategieSuppression;
import service.FileManager;
import service.ServiceManager;

public abstract class PaneViewElement<T> extends GridPane implements ViewElement<T> {

	protected final FileManager fm = ServiceManager.getInstance(FileManager.class);

	private int index;
	private final Map<Pane, T> paneMap = new HashMap<>();
	private final SortedInsert<Node> sort = new SortedInsert<>();
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
				sortedPane.addAll(change.getAddedSubList().stream().map(element -> (Pane) element).collect(Collectors.toList()));
			}
			else if(change.wasRemoved()) {
				sortedPane.removeAll(change.getRemoved().stream().map(element -> (Pane) element).collect(Collectors.toList()));
			}
		});
	}

	public abstract FXMLLoader createLoader() throws MalformedURLException;


	private void setOnChangeEvent(final Change<? extends T> change) {
		change.next();
		
		IStrategiePaneViewElement<T> strategie;
		
		if(change.wasAdded()) {
			strategie = new StrategieAjout<T>(this);
			
			change.getAddedSubList().stream().forEach(strategie::execute);
		}

		if(change.wasRemoved()) {
			strategie = new StrategieSuppression<T>(this);

			change.getRemoved().stream().forEach(strategie::execute);
		}
		
	}

	public void insertValueBasedOnSort(final Pane paneElement) {
		final var index = sort.getIndexInsertFromSort(sortedPane.stream().map(pane -> (Node) pane).collect(Collectors.toList()), paneElement);

		final var oldChild = (Pane) setChild(paneElement, index);

		sortedPane.sort(sort.getComparator());

		if(oldChild == null || oldChild == paneElement) {
			return;
		}

		insertValueBasedOnSort(oldChild);
	}

	public Node setChild(final Pane paneElement, final int index) {
		final var column = index % PaneViewElement.ELEMENTS_PER_ROW;
		final var row = index / PaneViewElement.ELEMENTS_PER_ROW;

		final var old = getChildren().stream()
				.filter(child -> GridPane.getColumnIndex(child) == column && GridPane.getRowIndex(child) == row)
				.findFirst();

		if(old.isPresent()) {
			getChildren().remove(old.get());
		}

		add(paneElement, column, row);

		if(getRowConstraints().size() == row) {
			getRowConstraints().add(new RowConstraints(0, PaneViewElement.HEIGHT + PaneViewElement.HEIGHT_PADDING, PaneViewElement.HEIGHT + PaneViewElement.HEIGHT_PADDING, null, VPos.CENTER, false));
		}

		if(getColumnConstraints().size() == column) {
			getColumnConstraints().add(new ColumnConstraints(0, PaneViewElement.WIDTH + PaneViewElement.WIDTH_PADDING, PaneViewElement.WIDTH + PaneViewElement.WIDTH_PADDING, null, HPos.LEFT, false));
		}

		if(old.isEmpty()) {
			return null;
		}

		return old.get();
	}

	@Override
	public int getSelectedIndex() {
		return index;
	}

	@Override
	public List<T> getItems() {
		return elements;
	}
	
	public void selectItem(final Pane pane) {
		index = elements.indexOf(paneMap.get(pane));
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
	
	public List<Pane> getSortedPane() {
		return sortedPane;
	}

}