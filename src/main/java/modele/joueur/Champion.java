package modele.joueur;

public class Champion {

	private String championName;
	private String base64ChampionImage;

	public Champion(String championName, String championImage) {
		this.championName = championName;
		this.base64ChampionImage = championImage;
	}

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}

	public String getBase64ChampionImage() {
		return base64ChampionImage;
	}

	public void setBase64ChampionImage(String championImage) {
		this.base64ChampionImage = championImage;
	}

}
