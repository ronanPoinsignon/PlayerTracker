package modele.request.data;

public class SummonerInGame {

	private String summoner_id;
	private String summoner_name;
	private boolean in_game;
	private String game_id;
	private String encryption_key;
	private String game_type;
	private String champion_name;
	private String champion_image;

	public SummonerInGame() {

	}

	public SummonerInGame(final String summoner_id, final String summoner_name, final boolean in_game, final String game_id,
			final String encryption_key, final String game_type, final String champion_name, final String champion_image) {
		this.summoner_id = summoner_id;
		this.summoner_name = summoner_name;
		this.in_game = in_game;
		this.game_id = game_id;
		this.encryption_key = encryption_key;
		this.game_type = game_type;
		this.champion_name = champion_name;
		this.champion_image = champion_image;
	}

	public String getSummoner_id() {
		return summoner_id;
	}

	public void setSummoner_id(final String summonerId) {
		summoner_id = summonerId;
	}

	public String getSummoner_name() {
		return summoner_name;
	}

	public void setSummoner_name(final String summonerName) {
		summoner_name = summonerName;
	}

	public boolean isIn_game() {
		return in_game;
	}

	public void setIn_game(final boolean inGame) {
		in_game = inGame;
	}

	public String getGame_id() {
		return game_id;
	}

	public void setGame_id(final String gameId) {
		game_id = gameId;
	}

	public String getEncryption_key() {
		return encryption_key;
	}

	public void setEncryption_key(final String encryptionKey) {
		encryption_key = encryptionKey;
	}

	public String getGame_type() {
		return game_type;
	}

	public void setGame_type(final String game_type) {
		this.game_type = game_type;
	}

	public String getChampion_name() {
		return champion_name;
	}

	public void setChampion_name(final String champion_name) {
		this.champion_name = champion_name;
	}

	public String getChampion_image() {
		return champion_image;
	}

	public void setChampion_image(final String champion_image) {
		this.champion_image = champion_image;
	}

	@Override
	public String toString() {
		return "SummonerInGame [summoner_id=" + summoner_id + ", summoner_name=" + summoner_name + ", in_game="
				+ in_game + ", game_id=" + game_id + ", encryption_key=" + encryption_key + ", game_type=" + game_type
				+ ", champion_name=" + champion_name + "]";
	}

}