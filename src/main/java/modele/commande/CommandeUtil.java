package modele.commande;

import java.util.ArrayList;
import java.util.List;

import modele.affichage.ViewElement;
import modele.commande.exception.PlayerNotFoundException;
import modele.exception.JoueurDejaPresentException;
import modele.joueur.JoueurFx;

public class CommandeUtil<T> {

	public boolean add(final ViewElement<T> table, final T element) throws JoueurDejaPresentException {
		if(element == null) {
			return false;
		}
		if(table.getItems().contains(element)) {
			throw new JoueurDejaPresentException();
		}
		return table.getItems().add(element);
	}

	public boolean add(final ViewElement<T> table, final T element, final int index) throws JoueurDejaPresentException, UnsupportedOperationException,
	ClassCastException, NullPointerException, IllegalArgumentException {
		if(element == null) {
			return false;
		}
		if(table.getItems().contains(element)) {
			throw new JoueurDejaPresentException();
		}
		table.getItems().add(index, element);
		return true;
	}

	public List<T> addAll(final ViewElement<T> table, final List<T> elements) {
		final var elementsDejaPresents = new ArrayList<T>();
		for(final T element : elements) {
			try {
				this.add(table, element);
			} catch (final JoueurDejaPresentException e) {
				elementsDejaPresents.add(element);
			}
		}
		return elementsDejaPresents;
	}

	public T remove(final ViewElement<T> table, final int index) {
		return table.getItems().remove(index);
	}

	public boolean remove(final ViewElement<T> table, final T element) throws PlayerNotFoundException {
		if(!table.getItems().contains(element)) {
			throw new PlayerNotFoundException();
		}
		return table.getItems().remove(element);
	}

	public List<JoueurFx> removeAll(final ViewElement<JoueurFx> table) {
		final List<JoueurFx> listeVideosRm = new ArrayList<>(table.getItems());
		table.getItems().removeAll(listeVideosRm);
		return listeVideosRm;
	}

	public List<T> removeAll(final ViewElement<T> table, final List<T> elements) {
		final var elementsASuppr = new ArrayList<T>(elements);
		final var elementsNonPresents = new ArrayList<T>();
		for(final T element : elementsASuppr) {
			try {
				this.remove(table, element);
			} catch (final PlayerNotFoundException e) {
				elementsNonPresents.add(element);
			}
		}
		return elementsNonPresents;
	}

}