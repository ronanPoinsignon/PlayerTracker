package modele.joueur;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Platform;
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
	private transient ObjectProperty<Serveur> serverProperty;

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
		serverProperty = new SimpleObjectProperty<>(joueur.server);
		serverNameProperty = new SimpleStringProperty(joueur.server != null ? joueur.server.getServerId() : "");
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
	
	public ObjectProperty<Serveur> getServerProperty() {
		return serverProperty;
	}

	public StringProperty getServerNameProperty() {
		return serverNameProperty;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	@Override
	public void setNom(final String nom) {
		super.setNom(nom);
		Platform.runLater(() -> nomProperty.set(nom));
	}

	@Override
	public void setPseudo(final String pseudo) {
		super.setPseudo(pseudo);
		Platform.runLater(() -> pseudoProperty.set(pseudo));
	}

	@Override
	public void setPlayerId(final String playerId) {
		super.setPlayerId(playerId);
		Platform.runLater(() -> idProperty.set(playerId));
	}

	@Override
	public void setInGame(final boolean connected) {
		super.setInGame(connected);
		Platform.runLater(() -> isConnecteProperty.set(connected));
	}

	@Override
	public void setServer(final Serveur server) {
		super.setServer(server);
		Platform.runLater(() -> serverNameProperty.set(server != null ? server.getServerId() : ""));
	}

	private void writeObject(final ObjectOutputStream out) throws IOException {
		throw new NotSerializableException();
	}

	private void readObject(final ObjectInputStream in) throws IOException {
		throw new NotSerializableException();
	}
}