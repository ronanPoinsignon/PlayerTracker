package modele.affichage;

import java.net.MalformedURLException;

import javafx.fxml.FXMLLoader;
import modele.affichage.sortstrategy.NameSort;
import modele.event.tache.event.EventSortSelect;
import modele.joueur.JoueurFx;

public class PaneViewJoueurFx extends PaneViewElement<JoueurFx> {

	public PaneViewJoueurFx() {
		setId("joueursContainer");

		setSort(new NameSort<JoueurFx>());
		eventService.addListener(EventSortSelect.EVENT_SORT_SELECT, evt -> setSort(evt.getSort()));
	}

	@Override
	public FXMLLoader createLoader() throws MalformedURLException {
		final var template = fm.getFileFromResources("fxml/joueur.fxml");
		return new FXMLLoader(template.toURI().toURL());
	}
}
