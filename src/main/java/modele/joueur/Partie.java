package modele.joueur;

public class Partie {

	private String gameId;
	private String encryptionKey;

	public Partie() {

	}

	public Partie(String gameId, String encryptionKey) {
		this.gameId = gameId;
		this.encryptionKey = encryptionKey;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

}
