package modele.commande;

import java.util.Arrays;
import java.util.List;

import javafx.scene.control.TableView;
import service.SaveService;
import service.ServiceManager;

/**
 * @author ronan
 *
 */
public abstract class CommandeListe<T> extends CommandeTable<T> {

	protected List<T> elements;
	protected SaveService saveService = ServiceManager.getInstance(SaveService.class);

	protected CommandeListe(final TableView<T> table, final List<T> elements) {
		super(table);
		this.elements = elements;
	}

	protected CommandeListe(final TableView<T> table, final T element) {
		super(table);
		elements = Arrays.asList(element);
	}

}