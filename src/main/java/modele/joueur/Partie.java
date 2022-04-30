package modele.joueur;

public class Partie {

	private String gameId;
	private String encryptionKey;
	private Champion champion;

	public Partie() {

	}

	public Partie(String gameId, String encryptionKey, Champion champion) {
		this.gameId = gameId;
		this.encryptionKey = encryptionKey;
		this.champion = champion;
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

	public Champion getChampion() {
		return champion;
	}

	public void setChampion(Champion champion) {
		this.champion = champion;
	}

}
