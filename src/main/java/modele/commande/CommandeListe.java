package modele.commande;

import java.util.Arrays;
import java.util.List;

import modele.affichage.ViewElement;
import service.SaveService;
import service.ServiceManager;

/**
 * @author ronan
 *
 */
public abstract class CommandeListe<T> extends CommandeTable<T> {

	protected List<T> elements;
	protected SaveService saveService = ServiceManager.getInstance(SaveService.class);

	protected CommandeListe(ViewElement<T> table, List<T> elements) {
		super(table);
		this.elements = elements;
	}

	protected CommandeListe(ViewElement<T> table, T element) {
		super(table);
		elements = Arrays.asList(element);
	}

}