package modele.request.data;

public class SummonerInGame {

	private String summoner_id;
	private String summoner_name;
	private boolean in_game;
	private String game_id;
	private String encryption_key;
	private String game_type;

	public SummonerInGame() {

	}

	public SummonerInGame(String summoner_id, String summoner_name, boolean in_game, String game_id, String encryption_key, String game_type) {
		this.summoner_id = summoner_id;
		this.summoner_name = summoner_name;
		this.in_game = in_game;
		this.game_id = game_id;
		this.encryption_key = encryption_key;
		this.game_type = game_type;
	}

	public String getSummoner_id() {
		return summoner_id;
	}

	public void setSummoner_id(String summonerId) {
		summoner_id = summonerId;
	}

	public String getSummoner_name() {
		return summoner_name;
	}

	public void setSummoner_name(String summonerName) {
		summoner_name = summonerName;
	}

	public boolean isIn_game() {
		return in_game;
	}

	public void setIn_game(boolean inGame) {
		in_game = inGame;
	}

	public String getGame_id() {
		return game_id;
	}

	public void setGame_id(String gameId) {
		game_id = gameId;
	}

	public String getEncryption_key() {
		return encryption_key;
	}

	public void setEncryption_key(String encryptionKey) {
		encryption_key = encryptionKey;
	}

	public String getGame_type() {
		return game_type;
	}

	public void setGame_type(String game_type) {
		this.game_type = game_type;
	}

	@Override
	public String toString() {
		return "SummonerInGame [summonerId=" + summoner_id + ", summonerName=" + summoner_name + ", inGame=" + in_game
				+ ", gameId=" + game_id + ", encryptionKey=" + encryption_key + "]";
	}

}
