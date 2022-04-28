package modele.request;

public class PlayerData {

	private String playerId;
	private String playerPseudo;
	private boolean inGame;
	private int gameId;
	private String encryptionKey;

	public PlayerData(String playerId, String playerPseudo, boolean inGame, int gameId, String encryptionKey) {
		this.playerId = playerId;
		this.playerPseudo = playerPseudo;
		this.inGame = inGame;
		this.gameId = gameId;
		this.encryptionKey = encryptionKey;
	}

	public String getPlayerId() {
		return playerId;
	}

	public String getPlayerPseudo() {
		return playerPseudo;
	}

	public boolean isInGame() {
		return inGame;
	}

	public int getGameId() {
		return gameId;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

}
