package modele.request.data;

public class SummonerData {

	private String summoner_id;
	private String name;
	private boolean in_game;
	private String last_game;

	public SummonerData() {

	}

	public SummonerData(final String summoner_id, final String name, final boolean in_game, final String last_game) {
		this.summoner_id = summoner_id;
		this.name = name;
		this.in_game = in_game;
		this.last_game = last_game;
	}

	public String getSummoner_id() {
		return summoner_id;
	}

	public void setSummoner_id(final String summoner_id) {
		this.summoner_id = summoner_id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isIn_game() {
		return in_game;
	}

	public void setIn_game(final boolean in_game) {
		this.in_game = in_game;
	}

	public String getLast_game() {
		return last_game;
	}

	public void setLast_game(final String last_game) {
		this.last_game = last_game;
	}

	@Override
	public String toString() {
		return "SummonerData [summoner_id=" + summoner_id + ", name=" + name + ", in_game=" + in_game + ", last_game="
				+ last_game + "]";
	}

}