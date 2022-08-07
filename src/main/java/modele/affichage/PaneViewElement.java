package modele.affichage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.JoueurController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import modele.joueur.JoueurFx;
import service.FileManager;
import service.ServiceManager;

public class PaneViewElement extends GridPane implements ViewElement<JoueurFx> {

	private final ObservableList<JoueurFx> elements = FXCollections.observableArrayList(new ArrayList<>());
	private final FileManager fm = ServiceManager.getInstance(FileManager.class);

	public PaneViewElement() {
		setId("joueursContainer");

		elements.addListener((final ListChangeListener.Change<? extends JoueurFx> change) -> {
			change.next();

			if(!change.wasAdded()) {
				return;
			}

			final var template = fm.getFileFromResources("fxml/joueur.fxml");

			for(final JoueurFx joueur : change.getAddedSubList()) {
				FXMLLoader loader;
				Pane paneJoueur;
				final JoueurController controller;

				try {
					loader = new FXMLLoader(template.toURI().toURL());
					paneJoueur = loader.load();
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}

				controller = loader.getController();
				controller.setJoueur(joueur);

				final var position = calculateNextPosition();

				getChildren().add(paneJoueur);

				paneJoueur.setTranslateX(position[0]);
				paneJoueur.setTranslateY(position[1]);

				setPrefHeight(position[1] + 240);
			}
		});

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
	public ReadOnlyObjectProperty<JoueurFx> selectedItemProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<JoueurFx> getItems() {
		return elements;
	}

}
