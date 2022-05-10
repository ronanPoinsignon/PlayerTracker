package modele.joueur.etat;

import javafx.scene.image.Image;

public class Connecte extends AEtat {

	@Override
	public Image getImageConnecte() {
		return fm.getImageFromResource("images/pastille_verte.png");
	}

	@Override
	public IEtat next() {
		return new NonConnecte();
	}

}
