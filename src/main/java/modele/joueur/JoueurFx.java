package modele.joueur;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import modele.joueur.etat.Connecte;
import modele.joueur.etat.IEtat;
import modele.joueur.etat.NonConnecte;

public class JoueurFx extends Joueur {

	private static final long serialVersionUID = 109145397335591218L;

	private transient StringProperty idProperty;
	private transient StringProperty nomProperty;
	private transient StringProperty pseudoProperty;
	private transient BooleanProperty isConnecteProperty;

	private transient Joueur joueur;
	private transient IEtat etat;
	private transient ObjectProperty<Image> imageConnexion;

	public JoueurFx(Joueur joueur) {
		super(joueur.nom, joueur.pseudo);
		this.joueur = joueur;
		playerId = joueur.playerId;
		inGame = joueur.inGame;
		idProperty = new SimpleStringProperty(joueur.getPlayerId());
		nomProperty = new SimpleStringProperty(joueur.nom);
		pseudoProperty = new SimpleStringProperty(joueur.pseudo);
		isConnecteProperty = new SimpleBooleanProperty(joueur.inGame);
		if(isConnecteProperty.get()) {
			etat = new Connecte();
		} else {
			etat = new NonConnecte();
		}
		imageConnexion = new SimpleObjectProperty<>(etat.getImageConnecte());
		isConnecteProperty.addListener((obs, oldValue, newValue) -> {
			if(!oldValue.equals(newValue)) {
				etat = etat.next();
			}
			imageConnexion.set(etat.getImageConnecte());
		});
	}

	public ObjectProperty<Image> getImageConnexion() {
		return imageConnexion;
	}

	public StringProperty getIdProperty() {
		return idProperty;
	}

	public StringProperty getNomProperty() {
		return nomProperty;
	}

	public StringProperty getPseudoProperty() {
		return pseudoProperty;
	}

	public BooleanProperty getIsConnecteProperty() {
		return isConnecteProperty;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	@Override
	public void setNom(String nom) {
		super.setNom(nom);
		nomProperty.set(nom);
	}

	@Override
	public void setPseudo(String pseudo) {
		super.setPseudo(pseudo);
		pseudoProperty.set(pseudo);
	}

	@Override
	public void setPlayerId(String playerId) {
		super.setPlayerId(playerId);
		idProperty.set(playerId);
	}

	@Override
	public void setInGame(boolean connected) {
		super.setInGame(connected);
		isConnecteProperty.set(connected);
	}
}
