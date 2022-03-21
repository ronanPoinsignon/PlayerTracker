package modele.joueur.etat;

import java.io.IOException;

import javafx.scene.image.Image;

public interface IEtat {

	Image getImageEtat() throws IOException;
	IEtat next();
}
