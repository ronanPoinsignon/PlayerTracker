package modele.joueur;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Joueur {

	StringProperty id;
	StringProperty nom;
	StringProperty pseudo;
	BooleanProperty isConnecte;

	public Joueur() {
		id = new SimpleStringProperty();
		nom = new SimpleStringProperty();
		pseudo = new SimpleStringProperty();
		isConnecte = new SimpleBooleanProperty();
	}

	public Joueur(String pseudo, String nom) {
		id = new SimpleStringProperty();
		this.nom = new SimpleStringProperty(nom);
		this.pseudo = new SimpleStringProperty(pseudo);
		isConnecte = new SimpleBooleanProperty(false);
	}

	public StringProperty getId() {
		return id;
	}

	public StringProperty getNom() {
		return nom;
	}

	public StringProperty getPseudo() {
		return pseudo;
	}

	public BooleanProperty isConnected() {
		return isConnecte;
	}

	public void setNom(String nom) {
		this.nom.set(nom);
	}

	public void setPseudo(String pseudo) {
		this.pseudo.set(pseudo);
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public void setConnected(boolean connected) {
		isConnecte.set(connected);
	}

}