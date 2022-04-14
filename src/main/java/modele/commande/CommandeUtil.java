package modele.commande;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableView;
import modele.joueur.JoueurFx;

public class CommandeUtil {

	public boolean add(TableView<JoueurFx> table, JoueurFx joueur) throws JoueurDejaPresentException {
		if(joueur == null) {
			return false;
		}
		if(table.getItems().contains(joueur)) {
			throw new JoueurDejaPresentException();
		}
		return table.getItems().add(joueur);
	}

	public boolean add(TableView<JoueurFx> table, JoueurFx joueur, int index) throws JoueurDejaPresentException, UnsupportedOperationException,
	ClassCastException, NullPointerException, IllegalArgumentException {
		if(joueur == null) {
			return false;
		}
		if(table.getItems().contains(joueur)) {
			throw new JoueurDejaPresentException();
		}
		table.getItems().add(index, joueur);
		return true;
	}

	public List<JoueurFx> addAll(TableView<JoueurFx> table, List<JoueurFx> listeVideosASuppr) {
		ArrayList<JoueurFx> listeVideoDejaPresentes = new ArrayList<>();
		for(JoueurFx joueur : listeVideosASuppr) {
			try {
				this.add(table, joueur);
			} catch (JoueurDejaPresentException e) {
				listeVideoDejaPresentes.add(joueur);
			}
		}
		return listeVideoDejaPresentes;
	}

	public JoueurFx remove(TableView<JoueurFx> table, int index) {
		return table.getItems().remove(index);
	}

	public boolean remove(TableView<JoueurFx> table, JoueurFx joueur) throws PlayerNotFoundException {
		if(!table.getItems().contains(joueur)) {
			throw new PlayerNotFoundException();
		}
		return table.getItems().remove(joueur);
	}

	public List<JoueurFx> removeAll(TableView<JoueurFx> table) {
		List<JoueurFx> listeVideosRm = new ArrayList<>(table.getItems());
		table.getItems().removeAll(listeVideosRm);
		return listeVideosRm;
	}

	public List<JoueurFx> removeAll(TableView<JoueurFx> table, List<JoueurFx> listeVideos) {
		ArrayList<JoueurFx> listeVideosASuppr = new ArrayList<>(listeVideos);
		ArrayList<JoueurFx> listeVideoNonPresentes = new ArrayList<>();
		for(JoueurFx joueur : listeVideosASuppr) {
			try {
				this.remove(table, joueur);
			} catch (PlayerNotFoundException e) {
				listeVideoNonPresentes.add(joueur);
			}
		}
		return listeVideoNonPresentes;
	}

}
