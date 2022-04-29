package modele.request.data;

public class SummonerData {

	private String summoner_id;
	private String name;
	private boolean in_game;
	private int last_game;
	private String last_update;
	private String id;
	private String toString;

	public SummonerData() {

	}

	public SummonerData(String summoner_id, String name, boolean in_game, int last_game, String last_update, String id,
			String toString) {
		this.summoner_id = summoner_id;
		this.name = name;
		this.in_game = in_game;
		this.last_game = last_game;
		this.last_update = last_update;
		this.id = id;
		this.toString = toString;
	}

	public String getSummoner_id() {
		return summoner_id;
	}

	public void setSummoner_id(String summoner_id) {
		this.summoner_id = summoner_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIn_game() {
		return in_game;
	}

	public void setIn_game(boolean in_game) {
		this.in_game = in_game;
	}

	public int getLast_game() {
		return last_game;
	}

	public void setLast_game(int last_game) {
		this.last_game = last_game;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToString() {
		return toString;
	}

	public void setToString(String toString) {
		this.toString = toString;
	}

	@Override
	public String toString() {
		return "SummonerData [\n\t\tsummoner_id=" + summoner_id + "\n\t\tname=" + name + "\n\t\tin_game=" + in_game + "\n\t\tlast_game="
				+ last_game + "\n\t\tlast_update=" + last_update + "\n\t\tid=" + id + "\n\t\ttoString=" + toString + "\n\t]";
	}

}
