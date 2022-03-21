package modele.joueur.etat;

import java.io.IOException;

import javafx.scene.image.Image;

public class Connecte extends AEtat {

	@Override
	public Image getImageEtat() throws IOException {
		return fm.getImageFromResource("images/pastille_verte.PNG");
	}

	@Override
	public IEtat next() {
		return new NonConnecte();
	}

}
