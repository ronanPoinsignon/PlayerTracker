package modele.joueur.etat;

import java.io.IOException;

import javafx.scene.image.Image;

public class NonConnecte extends AEtat {

	@Override
	public Image getImageEtat() throws IOException {
		return fm.getImageFromResource("images/pastille_rouge.jpg");
	}

	@Override
	public IEtat next() {
		return new Connecte();
	}

}
