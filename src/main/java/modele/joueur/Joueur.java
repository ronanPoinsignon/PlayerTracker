package modele.joueur;

public class Joueur {

	String id;
	String nom;
	String pseudo;
	boolean isConnecte;

	public Joueur() {
		id = "";
		nom = "";
		pseudo = "";
		isConnecte = false;
	}

	public Joueur(String nom, String pseudo) {
		id = "";
		this.nom = nom;
		this.pseudo = pseudo;
		isConnecte = false;
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public boolean isConnected() {
		return isConnecte;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setConnected(boolean isConnecte) {
		this.isConnecte = isConnecte;
	}

}