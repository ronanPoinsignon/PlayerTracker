package modele.joueur;

public class Partie {

	private String gameId;
	private String encryptionKey;
	private Champion champion;

	public Partie() {

	}

	public Partie(final String gameId, final String encryptionKey, final Champion champion) {
		this.gameId = gameId;
		this.encryptionKey = encryptionKey;
		this.champion = champion;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(final String gameId) {
		this.gameId = gameId;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(final String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public Champion getChampion() {
		return champion;
	}

	public void setChampion(final Champion champion) {
		this.champion = champion;
	}

}