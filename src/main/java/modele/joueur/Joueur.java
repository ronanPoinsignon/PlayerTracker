package modele.joueur;

import java.io.Serializable;

public class Joueur implements Serializable {

	private static final long serialVersionUID = -8240099841397326986L;

	protected String playerId;
	protected String nom;
	protected String pseudo;
	protected transient boolean inGame;
	protected transient Partie partie;

	public Joueur(final String nom, final String pseudo) {
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

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public void setPseudo(final String pseudo) {
		this.pseudo = pseudo;
	}

	public void setPlayerId(final String playerId) {
		this.playerId = playerId;
	}

	public void setInGame(final boolean inGame) {
		this.inGame = inGame;
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(final Partie partie) {
		this.partie = partie;
	}

	public String getAppellation() {
		return nom == null || nom.trim().isEmpty() ? pseudo : nom;
	}

	@Override
	public String toString() {
		return "Joueur [playerId=" + playerId + ", nom=" + nom + ", pseudo=" + pseudo + "]";
	}

}