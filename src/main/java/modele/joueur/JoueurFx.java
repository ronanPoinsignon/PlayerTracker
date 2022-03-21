package modele.joueur;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import modele.joueur.etat.Connecte;
import modele.joueur.etat.IEtat;
import modele.joueur.etat.NonConnecte;

public class JoueurFx extends Joueur {

	private Joueur joueur;
	private IEtat etat;
	private ObjectProperty<Image> imageConnexion;

	public JoueurFx(Joueur joueur) throws IOException {
		this.joueur = joueur;
		id.bind(joueur.id);
		nom.bind(joueur.nom);
		pseudo.bind(joueur.pseudo);
		isConnecte.bind(joueur.isConnecte);
		if(isConnecte.get()) {
			etat = new Connecte();
		} else {
			etat = new NonConnecte();
		}
		imageConnexion = new SimpleObjectProperty<>(etat.getImageEtat());
		joueur.isConnected().addListener((obs, oldValue, newValue) -> {
			if(!oldValue.equals(newValue)) {
				etat = etat.next();
			}
			try {
				imageConnexion.set(etat.getImageEtat());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public ObjectProperty<Image> getImageConnexion() {
		return imageConnexion;
	}

	@Override
	public void setNom(String nom) {
		joueur.setNom(nom);
	}

	@Override
	public void setPseudo(String pseudo) {
		joueur.setPseudo(pseudo);
	}

	@Override
	public void setId(String id) {
		joueur.setId(id);
	}

	@Override
	public void setConnected(boolean connected) {
		joueur.setConnected(connected);
	}
}
