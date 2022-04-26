package modele.joueur;

public class Joueur {

	String playerId;
	String nom;
	String pseudo;
	boolean inGame;

	public Joueur(String nom, String pseudo) {
		playerId = "";
		this.nom = nom;
		this.pseudo = pseudo;
		inGame = false;
	}

	public String getPlayerId() {
		return playerId;
	}

	public String getNom() {
		return nom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public String getAppellation() {
		return nom == null || nom.trim().isEmpty() ? pseudo : nom;
	}
}