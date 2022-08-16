package modele.joueur;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

	private static final long serialVersionUID = 2397910852525053894L;

	private transient StringProperty idProperty;
	private transient StringProperty nomProperty;
	private transient StringProperty pseudoProperty;
	private transient BooleanProperty isConnecteProperty;
	private transient StringProperty serverNameProperty;
	private transient StringProperty gameTypeProperty;

	private transient Joueur joueur;
	private transient IEtat etat;
	private transient ObjectProperty<Image> imageConnexion;

	public JoueurFx(final Joueur joueur) {
		super(joueur.nom, joueur.pseudo, joueur.server);
		this.joueur = joueur;
		playerId = joueur.playerId;
		nom = joueur.nom;
		pseudo = joueur.pseudo;
		inGame = joueur.inGame;
		partie = joueur.partie;
		server = joueur.server;

		idProperty = new SimpleStringProperty(joueur.getPlayerId());
		nomProperty = new SimpleStringProperty(joueur.nom);
		pseudoProperty = new SimpleStringProperty(joueur.pseudo);
		isConnecteProperty = new SimpleBooleanProperty(joueur.inGame);
		serverNameProperty = new SimpleStringProperty(joueur.server != null ? joueur.server.getServerId() : "");
		gameTypeProperty = new SimpleStringProperty(joueur.partie != null ? joueur.partie.getGameType() : "");
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

	public StringProperty getServerNameProperty() {
		return serverNameProperty;
	}
	public StringProperty getGameTypeProperty() {
		return gameTypeProperty;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	@Override
	public void setNom(final String nom) {
		super.setNom(nom);
		joueur.setNom(nom);
		nomProperty.set(nom);
	}

	@Override
	public void setPseudo(final String pseudo) {
		super.setPseudo(pseudo);
		joueur.setPseudo(pseudo);
		pseudoProperty.set(pseudo);
	}

	@Override
	public void setPlayerId(final String playerId) {
		super.setPlayerId(playerId);
		joueur.setPlayerId(playerId);
		idProperty.set(playerId);
	}

	@Override
	public void setInGame(final boolean connected) {
		super.setInGame(connected);
		joueur.setInGame(connected);
		isConnecteProperty.set(connected);
	}

	@Override
	public void setServer(final Serveur server) {
		super.setServer(server);
		joueur.setServer(server);
		serverNameProperty.set(server != null ? server.getServerId() : "");
	}

	@Override
	public void setPartie(final Partie partie) {
		super.setPartie(partie);
		joueur.setPartie(partie);
		gameTypeProperty.set(partie != null ? partie.getGameType() : "");
	}

	private void writeObject(final ObjectOutputStream out) throws IOException {
		throw new NotSerializableException();
	}

	private void readObject(final ObjectInputStream in) throws IOException {
		throw new NotSerializableException();
	}
}