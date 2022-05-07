package modele.commande;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;

public class CommandeUtil<T> {

	public boolean add(TableView<T> table, T element) throws JoueurDejaPresentException {
		if(element == null) {
			return false;
		}
		if(table.getItems().contains(element)) {
			throw new JoueurDejaPresentException();
		}
		return table.getItems().add(element);
	}

	public boolean add(TableView<T> table, T element, int index) throws JoueurDejaPresentException, UnsupportedOperationException,
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

	public List<T> addAll(TableView<T> table, List<T> elements) {
		ArrayList<T> elementsDejaPresents = new ArrayList<>();
		for(T element : elements) {
			try {
				this.add(table, element);
			} catch (JoueurDejaPresentException e) {
				elementsDejaPresents.add(element);
			}
		}
		return elementsDejaPresents;
	}

	public T remove(TableView<T> table, int index) {
		return table.getItems().remove(index);
	}

	public boolean remove(TableView<T> table, T element) throws PlayerNotFoundException {
		if(!table.getItems().contains(element)) {
			throw new PlayerNotFoundException();
		}
		return table.getItems().remove(element);
	}

	public List<JoueurFx> removeAll(TableView<JoueurFx> table) {
		List<JoueurFx> listeVideosRm = new ArrayList<>(table.getItems());
		table.getItems().removeAll(listeVideosRm);
		return listeVideosRm;
	}

	public List<T> removeAll(TableView<T> table, List<T> elements) {
		ArrayList<T> elementsASuppr = new ArrayList<>(elements);
		ArrayList<T> elementsNonPresents = new ArrayList<>();
		for(T element : elementsASuppr) {
			try {
				this.remove(table, element);
			} catch (PlayerNotFoundException e) {
				elementsNonPresents.add(element);
			}
		}
		return elementsNonPresents;
	}

}
