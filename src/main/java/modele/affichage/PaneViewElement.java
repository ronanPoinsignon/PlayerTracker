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
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import modele.affichage.sort.SortedInsert;
import modele.affichage.strategie.IStrategiePaneViewElement;
import modele.affichage.strategie.StrategieAjout;
import modele.affichage.strategie.StrategieSuppression;
import service.EventService;
import service.FileManager;
import service.ServiceManager;

public abstract class PaneViewElement<T> extends FlowPane implements ViewElement<T> {

	protected final FileManager fm = ServiceManager.getInstance(FileManager.class);
	protected final EventService eventService = ServiceManager.getInstance(EventService.class);

	private int index;
	private final Map<Pane, T> paneMap = new HashMap<>();
	private final SortedInsert<T> sort = new SortedInsert<>();
	private final ObservableList<T> elements = FXCollections.observableArrayList(new ArrayList<>());
	private final List<Pane> sortedPane = new ArrayList<>();

	protected static final int WIDTH = 300;
	protected static final int HEIGHT = 210;
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

		setHgap(PaneViewElement.WIDTH_PADDING);
		setVgap(PaneViewElement.HEIGHT_PADDING);
	}

	public abstract FXMLLoader createLoader() throws MalformedURLException;


	private void setOnChangeEvent(final Change<? extends T> change) {
		change.next();

		IStrategiePaneViewElement<T> strategie;

		if(change.wasAdded()) {
			strategie = new StrategieAjout<>(this);

			change.getAddedSubList().stream().forEach(strategie::execute);
		}

		if(change.wasRemoved()) {
			strategie = new StrategieSuppression<>(this);

			change.getRemoved().stream().forEach(strategie::execute);
		}

	}

	public void insertValueBasedOnSort(final Pane paneElement) {
		final var index = sort.getIndexInsertFromSort(sortedPane.stream().map(paneMap::get).collect(Collectors.toList()), paneMap.get(paneElement));

		getChildren().add(index, paneElement);

		sortedPane.sort((pane1, pane2) -> sort.getComparator().compare(paneMap.get(pane1), paneMap.get(pane2)));
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

	public void setSort(final Comparator<T> comparator) {
		this.sort.setComparator(comparator);
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