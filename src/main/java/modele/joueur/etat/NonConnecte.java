package modele.joueur.etat;

import javafx.scene.image.Image;

public class NonConnecte extends AEtat {

	@Override
	public Image getImageConnecte() {
		return fm.getImageFromResource("images/pastille_rouge.png");
	}

	@Override
	public IEtat next() {
		return new Connecte();
	}

}