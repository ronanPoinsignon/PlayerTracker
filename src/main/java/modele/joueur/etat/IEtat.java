package modele.joueur.etat;

import javafx.scene.image.Image;

public interface IEtat {

	Image getImageEtat();
	IEtat next();
}
