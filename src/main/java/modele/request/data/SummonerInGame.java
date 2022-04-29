package modele.request.data;

public class SummonerInGame {

	private String summonerId;
	private String summonerName;
	private boolean inGame;
	private String gameId;
	private String encryptionKey;

	public SummonerInGame() {

	}

	public SummonerInGame(String summonerId, String summonerName, boolean inGame, String gameId, String encryptionKey) {
		this.summonerId = summonerId;
		this.summonerName = summonerName;
		this.inGame = inGame;
		this.gameId = gameId;
		this.encryptionKey = encryptionKey;
	}

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
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

	@Override
	public String toString() {
		return "SummonerInGame [summonerId=" + summonerId + ", summonerName=" + summonerName + ", inGame=" + inGame
				+ ", gameId=" + gameId + ", encryptionKey=" + encryptionKey + "]";
	}

}
