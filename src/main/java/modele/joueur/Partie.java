package modele.joueur;

public class Partie {

	private String gameId;
	private String encryptionKey;
	private Champion champion;
	private String game_type;
	private long startTime;

	public Partie() {

	}

	public Partie(final String gameId, final String encryptionKey, final Champion champion, final String game_type, final long startTime) {
		this.gameId = gameId;
		this.encryptionKey = encryptionKey;
		this.champion = champion;
		this.game_type = game_type;
		this.startTime = startTime;
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

	public String getGameType() {
		return game_type;
	}

	public void setGameType(final String game_type) {
		this.game_type = game_type;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(final long startTime) {
		this.startTime = startTime;
	}

}